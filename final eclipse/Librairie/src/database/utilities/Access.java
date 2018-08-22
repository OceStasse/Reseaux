package database.utilities;

import java.beans.Statement;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

public class Access implements Serializable {
	/**
     * 
     */
    private static final long serialVersionUID = 1L;

	public static enum dataBaseType
	{
	    JDBC_ODBC_BRIDGE,
	    ORACLE_THIN,
	    ORACLE_OCI__8I,
	    ORACLE_OCI__9I,
	    CSV_FILES,
	    MYSQL,
	    SYBASE
	};
	
	private boolean transactionEnCours;
	private Connection connection;
	private Properties properties = null;
	private final String user;
	private final String pwd;
	private String URL;
	private String driver;
	
    public Access(String csvFilePath, String propertiesFilePath) throws FileNotFoundException, IOException 
    {
        setDriverUrl(dataBaseType.CSV_FILES, csvFilePath, "", "");
        this.setProperties(new Properties());
        this.getProperties().load(new FileInputStream(propertiesFilePath));
        this.user = "";
        this.pwd = "";
        this.setTransactionEnCours(false);
    }
    
    
    public Access(dataBaseType type, String ip, String port, String SID)
    {
        setDriverUrl(type, ip, port, SID);
        this.user = "";
        this.pwd = "";
        this.setTransactionEnCours(false);
    }

    public Access(dataBaseType type, String ip, String port, String SID, String schema, String password)
    {
        setDriverUrl(type, ip, port, SID);
        this.user = schema;
        this.pwd = password;
        this.setTransactionEnCours(false);
    }

    public boolean isTransactionEnCours() 
    {
	return transactionEnCours;
    }

    public void setTransactionEnCours(boolean transactionEnCours) {
	this.transactionEnCours = transactionEnCours;
    }

    public Connection getConnection() {
	return connection;
    }

    public void setConnection(Connection connection) {
	this.connection = connection;
    }

    public String getPwd() {
	return pwd;
    }

    public Properties getProperties() {
	return properties;
    }

    public void setProperties(Properties properties) {
	this.properties = properties;
    }

    public String getUser() {
	return user;
    }

    public String getURL() {
	return URL;
    }

    public void setURL(String uRL) {
	URL = uRL;
    }

    public String getDriver() {
	return driver;
    }

    public void setDriver(String driver) {
	this.driver = driver;
    }

    /**
     * Permet d'initialiser les variables membres driver et URL qui dépendent du type de base de données avec laquelle communiquer.
     * @param type type de la base de données (de l'enum databaseType
     * @param ip ip ou filepath dans le cas du CSV
     * @param port port sur lequel se connecter
     * @param SID SID pour oracle, ou databaseName dans d'autres cas
     */
    
    private void setDriverUrl(dataBaseType type, String ip, String port, String SID){
	switch(type){
	case JDBC_ODBC_BRIDGE:
	    this.setDriver("sun.jdbc.odbc.JdbcOdbcDriver");
	    this.setURL("jdbc:odbc:" + SID);
	    break;
	case ORACLE_THIN:
	    this.setDriver("oracle.jdbc.driver.OracleDriver");
	    this.setURL("jdbc:oracle:thin:@" + ip + ":" + port + ":" + SID);
	    break;
	case ORACLE_OCI__8I:
	    this.setDriver("oracle.jdbc.driver.OracleDriver");
	    this.setURL("jdbc:oracle:oci8:@" + SID);
	    break;
	case ORACLE_OCI__9I:
	    this.setDriver("oracle.jdbc.driver.OracleDriver");
	    this.setURL("jdbc:oracle:oci:@" + SID);
	    break;
	case CSV_FILES:
	    this.setDriver("org.relique.jdbc.csv.CsvDriver");
	    this.setURL("jdbc:relique:csv:" + ip);
	    setProperties(new Properties());
	    break;
	case MYSQL:
	    this.setDriver("org.gjt.mm.mysql.Driver");
	    this.setURL("jdbc:mysql://" + ip + ":" + port + "/"+ SID);
	    break;
	case SYBASE:
	    this.setDriver("com.sybase.jdbc2.jdbc.SybDriver");
	    this.setURL("jdbc:sybase:Tds:" + ip + ":" + port);
	    break;
	}

	System.err.println("Driver used : " + this.getDriver());
	System.err.println("URl : " + this.getURL());
    }

    /**
     * Charge le driver nécéssaire et se connecte à la base de données en mode autoCommit(false).
     * @throws ClassNotFoundException 
     * @throws SQLException Lancé dans les cas suivants: une erreur d'accès à la base de données, l'url est null (URL), l'autoCommit echoue.
     */
    
    public synchronized void connect() throws ClassNotFoundException, SQLException {
	try {
	    Class.forName(this.getDriver());
	    System.err.println("Driver loaded successfully!");
	    setConnection(DriverManager.getConnection(this.getURL(), this.getUser(), this.getPwd()));
	    getConnection().setAutoCommit(false);
	    System.err.println("Connection established!");
	} catch (ClassNotFoundException | SQLException ex) {
	    System.err.println(ex.getMessage());
	    throw ex;
	}
    }

    /**
     * close the connection to the data base.
     */
    
    public synchronized void disconnect() {
	if(this.getConnection() != null){
	    try{
		this.getConnection().close();
	    } catch (SQLException ex) {
		System.err.println(ex.getMessage());
	    }
	}
	this.setConnection(null);
    }

    /**
     * Active/désactive l'autocommit de la connection actuelle à la base de données.
     * @param autocommit booléen représentant l'activation
     * @throws SQLException
     */

    public synchronized void setAutoCommit(boolean autocommit) throws SQLException {
	if(this.getConnection() != null) 
	    this.getConnection().setAutoCommit(autocommit);
    }

    /**
     * Execute un commit sur la connection actuelle à la base de données.
     * @throws SQLException
     */
    public synchronized void commit() throws SQLException {
	System.err.println("COMMITING");
	if(this.getConnection() != null) 
	    this.getConnection().commit();
	this.transactionEnCours = false;
	notify();
    }

    /**
     * Effectue un rollback sur la connection actuelle à la base de données
     * @throws SQLException
     */
    public synchronized void rollback() throws SQLException {
	System.err.println("ROLLING BACK");
	if(this.getConnection() != null) 
	    this.getConnection().rollback();
	this.transactionEnCours = false;
	notify();
    }

    /**
     * Instancie un PreparedStatement et le prépare à l'aide de la requête passée en paramètre. Cette requête contient des '?'
     * @param sql requête au format SQL
     * @return L'instance du PreparedStatement préparé
     */
    public PreparedStatement getPreparedStatement(String sql) {
	PreparedStatement preparedStatement = null;

	try{
	    preparedStatement = this.getConnection().prepareStatement(sql);
	}catch(SQLException ex){
	    System.err.println(ex.getMessage());
	}

	return preparedStatement;
    }

    /**
     * Remplace les '?' par les Objets se trouvant dans la Map passée en paramètre
     * @param preparedStatement un instance déjà préparée (à l'aide de getPreparedStatement)
     * @param param Une map contenant les objets pour remplacer les '?' se trouvant dans la requête de la PreparedStatement
     * @return Le PreparedStatement modifié
     */
    public PreparedStatement buildPreparedStatement(PreparedStatement preparedStatement, Map<Integer, Object> param) {
	if(!param.isEmpty()){
	    try{
		for(Integer key : param.keySet()){
		    preparedStatement.setObject(key, param.get(key));
		}
	    }catch(SQLException ex){
		System.err.println(ex.getMessage());
	    }
	}

	return preparedStatement;
    }

    /**
     * Créé un Statement depuis la Connection
     * @return Le Statement créé
     * @throws SQLException
     */
    public Statement getStatement() throws SQLException {
	Statement statement = null;

	try{
	    statement = (Statement) this.getConnection().createStatement();
	} catch (SQLException ex) {
	    System.err.println(ex.getMessage());
	    throw ex;
	}

	return statement;
    }

    /**
     * Exécute une requête SQL reçue en paramètre
     * @param sql La requête à exécuter
     * @return Un ResultSet contenant les résultats de la requête
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(String sql) throws SQLException
    {
	ResultSet resultSet = null;

	try
	{
	    System.err.println("Executing query [" + sql + "]");
	    resultSet = ((java.sql.Statement) this.getStatement()).executeQuery(sql);
	}
	catch (SQLException ex) 
	{
	    System.err.println(ex.getMessage());
	    throw ex;
	}
	return resultSet;
    }

    /**
     * Exécute la requête SQL se trouvant dans la PreparedStatement passée en paramètre. La Map permet de remplacer les '?' de la requête SQL
     * @param preparedStatement La PreparedStatement contenant la requête SQL à executer 
     * @param param La Map contenant les Objects nécéssaire au remplacement des '?'
     * @return Un ResultSet contenant les résultats de la requête
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(PreparedStatement preparedStatement, Map<Integer, Object> param) throws SQLException 
    {
	ResultSet cursor = null;

	this.buildPreparedStatement(preparedStatement, param);
	try
	{
	    System.err.println("Executing query [" + preparedStatement + "]");
	    cursor = preparedStatement.executeQuery();
	    preparedStatement.clearParameters();
	}
	catch(SQLException ex)
	{
	    System.err.println(ex.getMessage());
	    throw ex;
	}

	return cursor;
    }

    /**
     * Créé un PrepareStatement depuis la requête SQL, remplace les '?' par les objets de la Map, et execute cette PreparedStatement
     * @param sql La requête à executer
     * @param param La Map contenant les objets nécéssaires au remplacement des '?'
     * @return Un ResultSet contenant les résultats de la requête
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(String sql, Map<Integer, Object> param) throws SQLException 
    {
	return this.executeQuery(this.getPreparedStatement(sql), param);
    }

    /**
     * Execute une requête SQL de type LMD ou LDD
     * @param sql la requête à exécuter
     * @return le nombre de lignes affectées/modifiées par la requête, ou 0
     * @throws SQLException
     * @throws java.lang.InterruptedException
     */
    public synchronized int executeUpdate(String sql) throws SQLException, InterruptedException 
    {
	while(!this.getConnection().getAutoCommit() && this.transactionEnCours) wait();
	int rowNumber = 0;

	try
	{
	    System.err.println("Executing query [" + sql + "]");
	    rowNumber = ((java.sql.Statement) this.getStatement()).executeUpdate(sql);
	}
	catch (SQLException ex)
	{
	    System.err.println(ex.getMessage());
	    throw ex;
	}

	return rowNumber;
    }

    /**
     * Execute une PreparedStatement effectuant une LMD ou LDD après avoir remplacé les '?' à l'aide de la Map.
     * @param preparedStatement La PreparedStatement à executer
     * @param param Une Map contenant les Object nécéssaire au remplacement des '?'
     * @return Le nombre de lignes affectées/modifiées par la PreparedStatement, ou 0
     * @throws SQLException
     * @throws java.lang.InterruptedException
     */
    public synchronized int executeUpdate(PreparedStatement preparedStatement, Map<Integer, Object> param) 
	    		throws SQLException, InterruptedException 
    {
	while(!this.getConnection().getAutoCommit() && this.transactionEnCours) wait();
	int rowNumber = 0;
	try
	{
	    System.err.println("Executing query [" + preparedStatement + "]");
	    rowNumber = this.buildPreparedStatement(preparedStatement, param).executeUpdate();
	    preparedStatement.clearParameters();
	} 
	catch (SQLException ex) 
	{
	    System.err.println(ex.getMessage());
	    throw ex;
	}

	return rowNumber;
    }

    /**
     * Construit et exécute une PreparedStatement à l'aide de la requête SQL de type LMD ou LDD.
     * @param sql La requête LDD ou LMD à executer
     * @param param Une Map contenant les Object nécéssaire au remplacement des '?'
     * @return Le nombre de lignes affectées/modifiées par la requête SQL, ou 0
     * @throws SQLException
     * @throws java.lang.InterruptedException
     */
    public synchronized int executeUpdate(String sql, Map<Integer, Object> param) throws SQLException, InterruptedException 
    {
	return this.executeUpdate(this.getPreparedStatement(sql), param);
    }

    /**
     * Permet de récupérer les noms des tables présentes dans la base de données
     * @return Une liste des noms des tables de la base de données
     * @throws SQLException
     */
    public synchronized ArrayList<String> getTableNames() throws SQLException 
    {
	ArrayList<String> tableNameList = new ArrayList<>();

	try 
	{
	    DatabaseMetaData databaseMetaData = this.getConnection().getMetaData();
	    String[] types = new String[1];
	    types[0] = "TABLE";
	    ResultSet tables = databaseMetaData.getTables(this.getConnection().getCatalog(), this.getConnection().getSchema(), null, types);
	    while(tables.next())
	    {
		tableNameList.add(tables.getString(3)); //0=TABLE_TYPE, 1=TABLE_CAT, 2=TABLE_SCHEM et 3=TABLE_NAME.
	    }
	    return tableNameList;
	}
	catch (SQLException ex) 
	{
	    System.err.println(ex.getMessage());
	}
	return null;
    }

    /**
     * Récupère le nom des colonnes du ResultSet passé en paramètre
     * @param resultSet Le ResultSet dont il faut extraire les noms de colonnes
     * @return Un vecteur contenant les noms de colonnes du ResultSet
     * @throws SQLException
     */
    public Vector<String> getColumnNames(ResultSet resultSet) throws SQLException
    {
	ResultSetMetaData metaData = resultSet.getMetaData();
	Vector<String> columnNames = new Vector<>();

	int columnCount = metaData.getColumnCount();
	for(int i=1; i<=columnCount; i++)
	{
	    columnNames.add(metaData.getColumnName(i));
	}

	return columnNames;
    }

    /**
     * Construit et rempli un DefaultTableModel à partir des tuples récupéré dans le ResultSet
     * @param resultSet résultat d'une Query effectuée précédemment
     * @return Une instance de DefaultTableModel contenant les noms de colonnes ainsi qu'une ligne par tuple du ResultSet.
     * @throws SQLException
     */
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException 
    {
	int columnCount = resultSet.getMetaData().getColumnCount();
	Vector<Vector<Object>> data = new Vector<>();

	while(resultSet.next())
	{
	    Vector<Object> lineVector = new Vector<>();
	    for(int i=1; i<=columnCount; i++)
	    {
		lineVector.add(resultSet.getObject(i));
	    }
	    data.add(lineVector);
	}

	return new DefaultTableModel(data, this.getColumnNames(resultSet));
    }
    
}
