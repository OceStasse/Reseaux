package database.utilities;

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
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
import java.util.Properties;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class DatabaseAccess implements Serializable{
    public static enum databaseType{
        JDBC_ODBC_BRIDGE,
        ORACLE_THIN,
        ORACLE_OCI__8I,
        ORACLE_OCI__9I,
        MICROSOFT_SQL_SERVER,
        MICROSOFT_SQL_SERVER_JTURBO,
        MICROSOFT_SQL_SERVER_2000,
        CSV_FILES,
        MYSQL,
        POSTGRESQL,
        SYBASE
    };
    
    protected String URL;
    protected String driver;
    
    private boolean transactionIsPending;
    private Connection _connection;
    private Properties properties = null;
    private final String _user;
    private final String _password;
    
    public DatabaseAccess(String csvFilePath, String propertiesFilePath) throws FileNotFoundException, IOException {
        setDriverAndUrl(databaseType.CSV_FILES, csvFilePath, "", "");
        this.properties = new Properties();
        this.properties.load(new FileInputStream(propertiesFilePath));
        this._user = "";
        this._password = "";
        this.transactionIsPending = false;
    }
    
    public DatabaseAccess(databaseType type, String ip, String port, String SID){
        setDriverAndUrl(type, ip, port, SID);
        this._user = "";
        this._password = "";
        this.transactionIsPending = false;
    }

    public DatabaseAccess(databaseType type, String ip, String port, String SID, String schema, String password) {
        setDriverAndUrl(type, ip, port, SID);
        this._user = schema;
        this._password = password;
        this.transactionIsPending = false;
    }
    
    /**
     * Permet d'initialiser les variables membres driver et URL qui dépendent du type de base de données avec laquelle communiquer.
     * @param type type de la base de données (de l'enum databaseType
     * @param ip ip ou filepath dans le cas du CSV
     * @param port port sur lequel se connecter
     * @param SID SID pour oracle, ou databaseName dans d'autres cas
     */
    private void setDriverAndUrl(databaseType type, String ip, String port, String SID){
        switch(type){
            case JDBC_ODBC_BRIDGE:
                this.driver = "sun.jdbc.odbc.JdbcOdbcDriver";
                this.URL = "jdbc:odbc:" + SID;
                break;
            case ORACLE_THIN:
                this.driver = "oracle.jdbc.driver.OracleDriver";
                this.URL = "jdbc:oracle:thin:@" + ip + ":" + port + ":" + SID;
                break;
            case ORACLE_OCI__8I:
                this.driver = "oracle.jdbc.driver.OracleDriver";
                this.URL = "jdbc:oracle:oci8:@" + SID;
                break;
            case ORACLE_OCI__9I:
                this.driver = "oracle.jdbc.driver.OracleDriver";
                this.URL = "jdbc:oracle:oci:@" + SID;
                break;
            case MICROSOFT_SQL_SERVER:
                this.driver = "weblogic.jdbc.mssqlserver4.Driver";
                this.URL = "jdbc:weblogic:mssqlserver4:" + SID + "@" + ip + ":" + port;
                break;
            case MICROSOFT_SQL_SERVER_JTURBO:
                this.driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                this.URL = "jdbc:JTurbo://" + ip + ":" + port + "/" + SID;
                break;
            case MICROSOFT_SQL_SERVER_2000:
                this.driver = "com.ashna.jturbo.driver.Driver";
                this.URL = "jdbc:microsoft:sqlserver://" + ip + ":" + port;
                if(!SID.isEmpty()) this.URL += ";DatabaseName=" + SID;
                break;
            case CSV_FILES:
                this.driver = "org.relique.jdbc.csv.CsvDriver";
                this.URL = "jdbc:relique:csv:" + ip;
                properties = new Properties();
                break;
            case MYSQL:
                this.driver = "org.gjt.mm.mysql.Driver";
                this.URL = "jdbc:mysql://" + ip + ":" + port + "/"+ SID;
                break;
            case POSTGRESQL:
                this.driver = "org.postgresql.Driver";
                this.URL = "jdbc:postgresql://" + ip + ":" + port + "/"+ SID;
                break;
            case SYBASE:
                this.driver = "com.sybase.jdbc2.jdbc.SybDriver";
                this.URL = "jdbc:sybase:Tds:" + ip + ":" + port;
                break;
        }
        
        System.out.println("Driver used : " + this.driver);
        System.out.println("URl : " + this.URL);
    }
    
    /**
     * Charge le driver nécéssaire et se connecte à la base de données en mode autoCommit(false).
     * @throws ClassNotFoundException 
     * @throws SQLException Lancé dans les cas suivants: une erreur d'accès à la base de données, l'url est null (URL), l'autoCommit echoue.
     */
    public synchronized void connect() throws ClassNotFoundException, SQLException {
        try {
            Class.forName(this.driver);
            System.out.println("Driver loaded successfully!");
            setConnection(DriverManager.getConnection(this.URL, this._user, this._password));
            getConnection().setAutoCommit(false);
            System.out.println("Connection established!");
        } catch (ClassNotFoundException | SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
    }
    
    /**
     * Ferme la connection à la base de données.
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
     * Construit et rempli un DefaultTableModel à partir des tuples récupéré dans le ResultSet
     * @param resultSet résultat d'une Query effectuée précédemment
     * @return Une instance de DefaultTableModel contenant les noms de colonnes ainsi qu'une ligne par tuple du ResultSet.
     * @throws SQLException
     */
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        int columnCount = resultSet.getMetaData().getColumnCount();
        Vector<Vector<Object>> data = new Vector<>();
        
        while(resultSet.next()){
            Vector<Object> lineVector = new Vector<>();
            for(int i=1; i<=columnCount; i++){
                lineVector.add(resultSet.getObject(i));
            }
            data.add(lineVector);
        }
        
        return new DefaultTableModel(data, this.getColumnNames(resultSet));
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
        this.transactionIsPending = false;
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
        this.transactionIsPending = false;
        notify();
    }
    
    //<editor-fold defaultstate="collapsed" desc="Statements">
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
            statement = this.getConnection().createStatement();
        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
            throw ex;
        }
        
        return statement;
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="Executes">
    /**
     * Exécute une requête SQL reçue en paramètre
     * @param sql La requête à exécuter
     * @return Un ResultSet contenant les résultats de la requête
     * @throws SQLException
     */
    public synchronized ResultSet executeQuery(String sql) throws SQLException {
        ResultSet resultSet = null;
        
        try{
            System.out.println("Executing query [" + sql + "]");
            resultSet = this.getStatement().executeQuery(sql);
        } catch (SQLException ex) {
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
    public synchronized ResultSet executeQuery(PreparedStatement preparedStatement, Map<Integer, Object> param) throws SQLException {
        ResultSet cursor = null;
        
        this.buildPreparedStatement(preparedStatement, param);
        try{
            System.out.println("Executing query [" + preparedStatement + "]");
            cursor = preparedStatement.executeQuery();
            preparedStatement.clearParameters();
        }catch(SQLException ex){
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
    public synchronized ResultSet executeQuery(String sql, Map<Integer, Object> param) throws SQLException {
        return this.executeQuery(this.getPreparedStatement(sql), param);
    }
    
    /**
     * Execute une requête SQL de type LMD ou LDD
     * @param sql la requête à exécuter
     * @return le nombre de lignes affectées/modifiées par la requête, ou 0
     * @throws SQLException
     * @throws java.lang.InterruptedException
     */
    public synchronized int executeUpdate(String sql) throws SQLException, InterruptedException {
        while(!this.getConnection().getAutoCommit() && this.transactionIsPending) wait();
        int rowNumber = 0;
        
        try{
            System.out.println("Executing query [" + sql + "]");
            rowNumber = this.getStatement().executeUpdate(sql);
        } catch (SQLException ex) {
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
    public synchronized int executeUpdate(PreparedStatement preparedStatement, Map<Integer, Object> param) throws SQLException, InterruptedException {
        while(!this.getConnection().getAutoCommit() && this.transactionIsPending) wait();
        int rowNumber = 0;
        
        try{
            System.out.println("Executing query [" + preparedStatement + "]");
            rowNumber = this.buildPreparedStatement(preparedStatement, param).executeUpdate();
            preparedStatement.clearParameters();
        } catch (SQLException ex) {
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
    public synchronized int executeUpdate(String sql, Map<Integer, Object> param) throws SQLException, InterruptedException {
        return this.executeUpdate(this.getPreparedStatement(sql), param);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    /**
     * Permet de récupérer les noms des tables présentes dans la base de données
     * @return Une liste des noms des tables de la base de données
     * @throws SQLException
     */
    public synchronized ArrayList<String> getTableNames() throws SQLException {
        ArrayList<String> tableNameList = new ArrayList<>();
        
        try {
            DatabaseMetaData databaseMetaData = this.getConnection().getMetaData();
            String[] types = new String[1];
            types[0] = "TABLE";
            ResultSet tables = databaseMetaData.getTables(this.getConnection().getCatalog(), this.getConnection().getSchema(), null, types);
            while(tables.next()){
                tableNameList.add(tables.getString(3)); //0=TABLE_TYPE, 1=TABLE_CAT, 2=TABLE_SCHEM et 3=TABLE_NAME.
            }
        return tableNameList;
        } catch (SQLException ex) {
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
    public Vector<String> getColumnNames(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        
        int columnCount = metaData.getColumnCount();
        for(int i=1; i<=columnCount; i++){
            columnNames.add(metaData.getColumnName(i));
        }
        
        return columnNames;
    }
    
    /**
     * Retourne l'objet Connection permettant d'accéder à la base de données connectée
     * @return La connection à la base de données
     */
    public synchronized Connection getConnection() {
        return _connection;
    }

    /**
     * Modifie la variable membre Connection
     * @param newConnection la nouvelle connection
     */
    public synchronized void setConnection(Connection newConnection) {
        this._connection = newConnection;
    }
    //</editor-fold>
}
