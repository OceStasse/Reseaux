package reservation_billet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import database.entities.Vol;
import database.utilities.Access;

/**
 * Servlet implementation class ControleurServlet
 */
@WebServlet("/ControleurServlet")
public class ControleurServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private final int MAXINACTIVEINTERVAL = 120;

    private final String DB_IP = "reynders.xyz";
    private final String DB_PORT = "3306";
    private final String DB_SCHEMA = "BD_AIRPORT";
    private final String DB_USERNAME = "LaboReseaux";
    private final String DB_PASSWORD = "mysql";
    private final String DB_TABLE_LOGIN = "passenger";
    private final String DB_TABLE_CADDIE = "caddie";
    private final String DB_TABLE_RESERVATION = "caddieitem";
    private final String DB_TABLE_VOL = "flight";
    private final String DB_TABLE_AVION = "airplane";
    private final String DB_TABLE_BILLET = "ticket";
    private Access db;

    private ServletContext servletContext;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ControleurServlet() {
	super();
    }

    /**
     * @see Servlet#init(ServletConfig)
     */
    public void init(ServletConfig config) throws ServletException {
	super.init(config);

	this.servletContext = getServletContext();
	this.servletContext.log("-- démarrage de la ControllerServlet");

	try {
	    this.db = new Access(Access.dataBaseType.MYSQL, this.DB_IP, this.DB_PORT, this.DB_SCHEMA, this.DB_USERNAME, this.DB_PASSWORD);
	    this.db.connect();

	} catch (ClassNotFoundException ex) {
	    this.servletContext.log("CLASSNOTFOUND !?!", ex);
	} catch (SQLException ex) {
	    this.servletContext.log("SQLEXCEPTION !?!", ex);
	}
    }

    /**
     * @see Servlet#destroy()
     */
    public void destroy() {
	super.destroy();
	db = null;
    }



    /**
     * @see Servlet#getServletInfo()
     */
    public String getServletInfo() {
	return "Servlet controleur billet achat"; 
    }
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	System.out.println("\n\n\n\n\n--- Received request for page : " + request.getRequestURL());
	System.out.println("***** REQUEST items : ");
	Enumeration<String> requestAttributeNames = request.getParameterNames();
	while (requestAttributeNames.hasMoreElements()) {
	    String name = requestAttributeNames.nextElement();
	    Object value = request.getParameter(name);
	    System.out.println("              " + name + "=" + value);
	}
	HttpSession session = request.getSession(false);
	if(session == null){
	    requeteLogin(request, response);
	}
	else{
	    System.out.println("\n***** SESSION items : ");
	    Enumeration<String> sessionAttributeNames = session.getAttributeNames();
	    while (sessionAttributeNames.hasMoreElements()) {
		String name = sessionAttributeNames.nextElement();
		Object value = session.getAttribute(name);
		System.out.println("              " + name + "=" + value);
	    }
	    System.out.println("\n");

	    String action = request.getRequestURI().substring(request.getContextPath().length()+1);
	    switch(action){
	    case "init" :
		initRequete(request, response);
		break;
	    case "caddie" :
		caddieRequete(request, response);
		break;
	    case "pay" :
		payRequete(request, response);
		break;
	    case "payment" :
		this.servletContext.getRequestDispatcher("/WEB-INF/JSPPayment.jsp")
		.forward(request, response);
		break;
	    case "logout" :
		session.invalidate();
		response.sendRedirect(request.getContextPath() + "/");
		break;
	    default:
		if(session.getAttribute("idcaddie") != null)
		    response.sendRedirect(request.getContextPath() + "/caddie");
		else{
		    if(session.getAttribute("idpassenger") != null)
			response.sendRedirect(request.getContextPath() + "/init");
		    else
			affichagePage(response, "Action inconnue! (" + action + ")");
		}
	    }
	}
    }
    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	processRequest(request, response);
    }

    private void requeteLogin(HttpServletRequest request, HttpServletResponse response) {
	if(request.getParameter("inputLogin") == null){
	    try {
		// Arrive pour la 1ère fois sur la page login
		if(request.getParameter("nvCli") != null)
		    this.servletContext.getRequestDispatcher("/nouvelleUtilisateur.html")
		    .forward(request, response);
		else
		    this.servletContext.getRequestDispatcher("/login.html")
		    .forward(request, response);
	    } catch(ServletException | IOException ex) {
		Logger.getLogger(ControleurServlet.class.getName()).log(Level.SEVERE, null, ex);
		affichagePage(response, "ERREUR de forward depuis loginRequest.");
	    }
	}else{
	    if(request.getParameter("eid") != null)
		enregistrerClient(request, response);
	    else
		loginClient(request, response);
	}
    }

    private void enregistrerClient(HttpServletRequest request, HttpServletResponse response) {
	this.servletContext.log("New client requested to register.");

	try {
	    Map<Integer, Object> preparedMap = new HashMap<>();
	    preparedMap.put(1, (String)request.getParameter("inputLogin"));

	    // On vérifie si l'agent existe déjà
	    String sql = "SELECT password FROM " + this.DB_TABLE_LOGIN + " WHERE login=? FOR UPDATE";
	    ResultSet resultSet = this.db.executeQuery(sql, preparedMap);
	    if(resultSet.next()){
		this.servletContext.getRequestDispatcher("/newUserErr.html")
		.forward(request, response);
	    }
	    else{
		//L'agent n'existe pas encore ==> insert!
		preparedMap.put(2, request.getParameter("inputPassword"));
		preparedMap.put(3, request.getParameter("eid"));
		preparedMap.put(4, request.getParameter("firstname"));
		preparedMap.put(5, request.getParameter("lastname"));
		sql = "INSERT INTO " + this.DB_TABLE_LOGIN + "(login, password, idpassenger, firstname, lastname) VALUES (?, ?, ?, ?, ?)";

		if(this.db.executeUpdate(sql, preparedMap) == 1){
		    this.db.commit();
		    HttpSession session = request.getSession(true);
		    session.setAttribute("idpassenger", request.getParameter("eid"));
		    session.setAttribute("firstname", request.getParameter("firstname"));
		    session.setAttribute("lastname", request.getParameter("lastname"));
		    session.setMaxInactiveInterval(this.MAXINACTIVEINTERVAL);

		    response.sendRedirect(request.getContextPath() + "/init");
		}
		else{
		    this.db.rollback();
		    affichagePage(response, "ERREUR lors de l'ajout de vos identifiants dans la base de données!");
		}
	    }
	} catch (SQLException ex) {
	    this.servletContext.log("ERREUR de connexion à la base de données!", ex);
	    affichagePage(response, "ERREUR de connexion à la base de données!");
	} catch (ServletException | IOException ex) {
	    this.servletContext.log("ERREUR de forward depuis loginRequest.", ex);
	    affichagePage(response, "ERREUR de forward depuis loginRequest.");
	} catch (InterruptedException ex) {
	    this.servletContext.log("ERREUR SQL was interrupted.", ex);
	    affichagePage(response, "ERREUR SQL was interrupted.");
	}
    }

    private void loginClient(HttpServletRequest request, HttpServletResponse response) {
	this.servletContext.log("New client requested to login.");

	try {
	    Map<Integer, Object> preparedMap = new HashMap<>();
	    preparedMap.put(1, request.getParameter("inputLogin"));

	    String sql = "SELECT password, idpassenger, lastname, firstname FROM " + this.DB_TABLE_LOGIN + " WHERE login=?";
	    ResultSet resultSet = this.db.executeQuery(sql, preparedMap);

	    if( !resultSet.next() || !resultSet.getString("password")
		    .equals(request.getParameter("inputPassword")))
		this.servletContext.getRequestDispatcher("/loginErr.html")
		.forward(request, response);
	    else{
		HttpSession session = request.getSession(true);
		session.setAttribute("idpassenger", resultSet.getString("idpassenger"));
		session.setAttribute("firstname", resultSet.getString("firstname"));
		session.setAttribute("lastname", resultSet.getString("lastname"));
		session.setMaxInactiveInterval(this.MAXINACTIVEINTERVAL);

		response.sendRedirect(request.getContextPath() + "/init");
	    }
	} catch (SQLException ex) {
	    this.servletContext.log("ERREUR de connexion à la base de données!", ex);
	    affichagePage(response, "ERREUR de connexion à la base de données!");
	} catch (ServletException | IOException ex) {
	    this.servletContext.log("ERREUR de forward depuis loginRequest.", ex);
	    affichagePage(response, "ERREUR de forward depuis loginRequest.");
	}
    }

    private void initRequete(HttpServletRequest request, HttpServletResponse response) {
	this.servletContext.log("New client requested to init caddie.");
	request.setAttribute("pageTitle", "Creating your caddie...");
	try {
	    try{
		HttpSession session = request.getSession(false);

		if(session.getAttribute("idcaddie") != null){
		    response.sendRedirect(request.getContextPath() + "/caddie");
		    return;
		}
		else{
		    try {
			Map<Integer, Object> preparedMap = new HashMap<>();
			preparedMap.put(1, (String) session.getAttribute("idpassenger"));

			String sql = "SELECT idcaddie FROM " + this.DB_TABLE_CADDIE
				+ " WHERE fk_idpassenger = ? FOR UPDATE";
			ResultSet resultSet;
			resultSet = this.db.executeQuery(sql, preparedMap);

			if(resultSet.next()){
			    // Le client a déjà un caddie d'ouvert
			    // (session interrompue, session reprise d'un autre appareil, etc...)
			    this.db.rollback();
			    session.setAttribute("idcaddie", resultSet.getInt("idcaddie"));;
			}
			else{
			    // Le caddie n'existe pas encore ==> on le créé
			    sql = "INSERT INTO " + this.DB_TABLE_CADDIE + "(fk_idpassenger)"
				    + " VALUES(?)";
			    int result = this.db.executeUpdate(sql, preparedMap);

			    if(result != 1){
				this.db.rollback();
				affichagePage(response, "ERREUR de création du caddie dans la BdD!");
				return;
			    }
			    else{
				this.db.commit();
				// On va chercher l'id du caddie
				sql = "SELECT idcaddie FROM " + this.DB_TABLE_CADDIE
					+ " WHERE fk_idpassenger = ?";
				ResultSet newRes = this.db.executeQuery(sql, preparedMap);

				if( !newRes.next() )
				    request.setAttribute("msgErreur", "ERREUR de recuperation du nouvel id du caddie!");
				else{
				    session.setAttribute("idcaddie", newRes.getInt("idcaddie"));
				}
			    }
			}

		    } catch (SQLException ex) {
			this.servletContext.log("ERREUR de connexion à la base de données!", ex);
			request.setAttribute("msgErreur", "ERREUR de connexion à la base de données!");
		    } catch (InterruptedException ex) {
			this.servletContext.log("ERREUR SQL was interrupted.", ex);
			request.setAttribute("msgErreur", "ERREUR SQL was interrupted.");
		    }
		}
	    } catch (IOException ex) {
		this.servletContext.log("ERREUR de recuperation de la variable session!");
		request.setAttribute("msgErreur", "ERREUR de recuperation de la variable session!");
	    }

	    this.servletContext.getRequestDispatcher("/WEB-INF/JSPInit.jsp")
	    .forward(request, response);
	} catch (ServletException | IOException ex) {
	    this.servletContext.log("ERREUR de forward depuis initRequest.", ex);
	    affichagePage(response, "ERREUR de forward depuis initRequest.");
	}
    }

    private void caddieRequete(HttpServletRequest request, HttpServletResponse response) {
	this.servletContext.log("New client requested to init caddie.");
	request.setAttribute("pageTitle", "Book your vol!");

	try {
	    HttpSession session = request.getSession(false);

	    if(session.getAttribute("idcaddie") == null){
		response.sendRedirect(request.getContextPath() + "/init");
		return;
	    }

	    // On regarde si on demande à ajouter un vol
	    if(request.getParameter("ap") != null
		    && request.getParameter("al") != null
		    && request.getParameter("dep") != null
		    && request.getParameter("des") != null
		    && request.getParameter("rsvNbr") != null
		    && Integer.valueOf(request.getParameter("rsvNbr")) > 0 ){
		reserveVol(request, session);
	    }

	    // On récupère les items du caddie depuis la bd
	    chercherItemCaddie(request, session, false);

	    // On parcours les vols disponibles
	    chercherAvion(request, session);

	    this.servletContext.getRequestDispatcher("/WEB-INF/JSPCaddie.jsp")
	    .forward(request, response);
	} catch (ServletException | IOException ex) {
	    System.out.println(ex.getMessage());
	    this.servletContext.log("ERREUR de forward depuis initRequest.", ex);
	    affichagePage(response, "ERREUR de forward depuis initRequest.");
	} catch (SQLException ex) {
	    System.out.println(ex.getMessage());
	    this.servletContext.log("ERREUR de connexion à la base de données!", ex);
	    affichagePage(response, "ERREUR de connexion à la base de données!");
	}
    }

    private void reserveVol(HttpServletRequest request, HttpSession session) throws SQLException {
	String sql;
	ResultSet resultSet;
	HashMap<Integer, Object> preparedMap = new HashMap<>();

	preparedMap.put(1, request.getParameter("ap"));
	preparedMap.put(2, request.getParameter("al"));
	preparedMap.put(3, request.getParameter("dep"));
	preparedMap.put(4, request.getParameter("des"));
	sql = "SELECT seatsSold, seats"
		+ " FROM " + this.DB_TABLE_VOL + " INNER JOIN " + this.DB_TABLE_AVION
		+ "   ON " + this.DB_TABLE_VOL + ".fk_idairplane = " + this.DB_TABLE_AVION + ".idairplane"
		+ "   AND " + this.DB_TABLE_VOL + ".fk_idairline = " + this.DB_TABLE_AVION + ".fk_idairline"
		+ " WHERE fk_idairplane=?"
		+ "   AND " + this.DB_TABLE_VOL + ".fk_idairline=?"
		+ "   AND departure=?"
		+ "   AND destination=?"
		+ " FOR UPDATE";

	resultSet = this.db.executeQuery(sql, preparedMap);

	if(resultSet.next()){
	    if( (resultSet.getInt("seatsSold") + Integer.valueOf(request.getParameter("rsvNbr"))) <= resultSet.getInt("seats")){
		try {
		    sql = "UPDATE " + this.DB_TABLE_VOL
			    + " SET seatsSold=" + (resultSet.getInt("seatsSold") + Integer.valueOf(request.getParameter("rsvNbr")))
			    + " WHERE fk_idairplane=?"
			    + "   AND fk_idairline=?"
			    + "   AND departure=?"
			    + "   AND destination=?";

		    if(this.db.executeUpdate(sql, preparedMap ) == 1 ){
			//Si on a déjà réservé des places pour ce vol, on update le nombre
			preparedMap.put(5, session.getAttribute("idcaddie"));
			sql = "SELECT iditem, reservedSeats"
				+ " FROM " + this.DB_TABLE_RESERVATION
				+ " WHERE fk_idairplane=?"
				+ "   AND fk_idairline=?"
				+ "   AND fk_departure=?"
				+ "   AND fk_destination=?"
				+ "   AND fk_idcaddie=?"
				+ " FOR UPDATE";
			resultSet = this.db.executeQuery(sql, preparedMap);

			if(resultSet.next()){
			    sql = "UPDATE " + this.DB_TABLE_RESERVATION
				    + " SET reservedSeats = " + (resultSet.getInt("reservedSeats") + Integer.valueOf(request.getParameter("rsvNbr")))
				    + " WHERE iditem=" + resultSet.getInt("iditem");
			}
			else{
			    preparedMap.put(6, Integer.valueOf(request.getParameter("rsvNbr")));
			    sql = "INSERT INTO " + this.DB_TABLE_RESERVATION
				    + " (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idcaddie`, `reservedSeats`)"
				    + " VALUES(?, ?, ?, ?, ?, ?)";
			}
			if( this.db.executeUpdate(sql, preparedMap) == 1)
			    this.db.commit();
			else
			    this.db.rollback();
		    }
		    else
			this.db.rollback();
		} catch (SQLException | InterruptedException ex) {
		    System.out.println(ex.getMessage());
		    this.db.rollback();
		}
	    }
	    else
		this.db.rollback();
	}
    }

    private LinkedHashMap<Integer, Vol> chercherItemCaddie(HttpServletRequest request, HttpSession session, boolean forUpdate) throws SQLException {
	String sql;
	ResultSet resultSet;
	HashMap<Integer, Object> preparedMap = new HashMap<>();
	preparedMap.put(1, session.getAttribute("idcaddie"));

	sql = "SELECT iditem, " + this.DB_TABLE_RESERVATION + ".fk_idairline, " + this.DB_TABLE_RESERVATION + ".fk_idairplane, fk_departure, takeOffTime, distance, scheduledLanding, fk_destination, fk_idgeographiczone, reservedSeats, price"
		+ " FROM " + this.DB_TABLE_RESERVATION
		+ " INNER JOIN " + this.DB_TABLE_VOL
		+ "   ON " + this.DB_TABLE_RESERVATION + ".fk_idairplane = " + this.DB_TABLE_VOL + ".fk_idairplane"
		+ "   AND " + this.DB_TABLE_RESERVATION + ".fk_idairline = " + this.DB_TABLE_VOL + ".fk_idairline"
		+ "   AND " + this.DB_TABLE_RESERVATION + ".fk_departure = " + this.DB_TABLE_VOL + ".departure"
		+ "   AND " + this.DB_TABLE_RESERVATION + ".fk_destination = " + this.DB_TABLE_VOL + ".destination"
		+ " WHERE fk_idcaddie = ?";
	if(forUpdate)
	    sql += " FOR UPDATE";

	resultSet = this.db.executeQuery(sql, preparedMap);
	LinkedHashMap<Integer, Vol> vols = new LinkedHashMap<>();
	while(resultSet.next()){
	    vols.put(resultSet.getInt("iditem"),
		    new Vol(resultSet.getInt("fk_idairplane"),
			    resultSet.getString("fk_idairline"),
			    Date.valueOf(resultSet.getDate("fk_departure").toLocalDate()),
			    resultSet.getString("fk_destination"),
			    resultSet.getString("fk_idgeographiczone"),
			    resultSet.getInt("distance"),
			    Time.valueOf(resultSet.getTime("takeOffTime").toLocalTime()),
			    Time.valueOf(resultSet.getTime("scheduledLanding").toLocalTime()),
			    resultSet.getInt("reservedSeats"),
			    resultSet.getDouble("price"))
		    );
	}

	request.setAttribute("flights", vols);
	return vols;
    }

    private void payRequete(HttpServletRequest request, HttpServletResponse response) {
	this.servletContext.log("New client requested to init caddie.");
	request.setAttribute("pageTitle", "Money, money, money!");

	try {
	    HttpSession session = request.getSession(false);

	    if(session.getAttribute("idcaddie") == null){
		response.sendRedirect(request.getContextPath() + "/init");
	    }
	    else{
		try{
		    if(request.getParameter("action") != null){
			switch(request.getParameter("action")){
			case "validatePayment":
			    for(Map.Entry<Integer, Vol> entry : chercherItemCaddie(request, session, true).entrySet()){
				HashMap<Integer, Object> preparedMap = new HashMap<>();
				preparedMap.put(1, entry.getValue().getIdAvion());
				preparedMap.put(2, entry.getValue().getIdCompagnieAvion());
				preparedMap.put(3, entry.getValue().getDepartDate());
				preparedMap.put(4, entry.getValue().getDestination());

				String sql = "SELECT idticket, nbaccompagnant"
					+ " FROM " + this.DB_TABLE_BILLET
					+ " WHERE fk_idairplane=?"
						+ "   AND fk_idairline=?"
							+ "   AND fk_departure=?"
							+ "   AND fk_destination=?"
							+ " ORDER BY idticket DESC LIMIT 1"
							+ " FOR UPDATE";

				ResultSet rs = this.db.executeQuery(sql, preparedMap);
				if(rs.next()){
				    System.out.println("TROUVé un autre ticket pour ce vol :" + rs.getInt("idticket"));
				    preparedMap.put(5, (rs.getInt("idticket")+rs.getInt("nbaccompagnant")+1));
				}
				else{
				    System.out.println("pas d'autres tickets pour ce vol");
				    preparedMap.put(5, 1);
				}

				preparedMap.put(6, session.getAttribute("idpassenger"));
				preparedMap.put(7, (entry.getValue().getPlace()-1));
				sql = "INSERT INTO " + this.DB_TABLE_BILLET
					+ "(`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`,`idticket`,`fk_idpassenger`,`nbaccompagnant`)"
					+ " VALUES(?,?,?,?,?,?,?)";

				if(this.db.executeUpdate(sql, preparedMap)==1){
				    preparedMap.clear();
				    preparedMap.put(1, entry.getKey());
				    sql = "DELETE FROM " + this.DB_TABLE_RESERVATION
					    + " WHERE iditem=?";

				    this.db.executeUpdate(sql, preparedMap);
				}
			    }
			    this.db.commit();
			    affichagePage(response, "Votre payement a bien été pris en compte!");
			    return;
			case "emptyCaddie":
			    HashMap<Integer, Object> preparedMap = new HashMap<>();
			    preparedMap.put(1, session.getAttribute("idcaddie"));

			    String sql = "UPDATE " + this.DB_TABLE_RESERVATION + " INNER JOIN " + this.DB_TABLE_VOL
				    + "	ON " + this.DB_TABLE_RESERVATION + ".fk_idairplane = " + this.DB_TABLE_VOL + ".fk_idairplane"
				    + " AND " + this.DB_TABLE_RESERVATION + ".fk_idairline = " + this.DB_TABLE_VOL + ".fk_idairline"
				    + " AND " + this.DB_TABLE_RESERVATION + ".fk_departure = " + this.DB_TABLE_VOL + ".departure"
				    + " AND " + this.DB_TABLE_RESERVATION + ".fk_destination = " + this.DB_TABLE_VOL + ".destination"
				    + " SET seatsSold=seatsSold-reservedSeats"
				    + " WHERE fk_idcaddie=?";

			    if(this.db.executeUpdate(sql, preparedMap) >0 ){
				sql = "DELETE FROM " + this.DB_TABLE_RESERVATION
					+ " WHERE fk_idcaddie=?";

				this.db.executeUpdate(sql, preparedMap);
				this.db.commit();
			    }
			    else
				this.db.rollback();

			    affichagePage(response, "Votre caddie a bien été vidé!");
			    return;
			default:
			    this.servletContext.getRequestDispatcher("/WEB-INF/JSPPay.jsp")
			    .forward(request, response);
			    break;
			}
		    }
		    else{
			int totalPrice = 0;
			for(Vol vol : chercherItemCaddie(request, session, true).values()){
			    totalPrice += vol.getPrix();
			}

			request.setAttribute("totalPrice", totalPrice);
			session.setAttribute("totalPrice", totalPrice);
			this.servletContext.getRequestDispatcher("/WEB-INF/JSPPay.jsp")
			.forward(request, response);
		    }
		} catch (InterruptedException | SQLException ex) {
		    this.servletContext.log("ERREUR de connexion à la base de données!", ex);
		    try {
			this.db.rollback();
		    } catch (SQLException ex1) {
			System.out.println(ex1.getMessage());
		    }
		    affichagePage(response, "ERREUR de connexion à la base de données!");
		}
	    }
	} catch (ServletException | IOException ex) {
	    System.out.println(ex.getMessage());
	    this.servletContext.log("ERREUR de forward depuis initRequest.", ex);
	    affichagePage(response, "ERREUR de forward depuis initRequest.");
	}

    }

    private LinkedHashMap<Vol, Integer> chercherAvion(HttpServletRequest request, HttpSession session) throws SQLException {
	String sql;
	ResultSet resultSet;
	LinkedHashMap<Vol, Integer> availableVols = new LinkedHashMap<>();

	sql = "SELECT fk_idairplane, " + this.DB_TABLE_VOL + ".fk_idairline, departure, destination, fk_idgeographiczone, distance, takeOffTime, scheduledLanding, seatsSold, seats, price"
		+ " FROM " + this.DB_TABLE_VOL + " INNER JOIN " + this.DB_TABLE_AVION
		+ "   ON " + this.DB_TABLE_VOL + ".fk_idairplane = " + this.DB_TABLE_AVION + ".idairplane"
		+ "   AND " + this.DB_TABLE_VOL + ".fk_idairline = " + this.DB_TABLE_AVION + ".fk_idairline"
		+ " WHERE TIMESTAMP(departure, takeOffTime)>=NOW()"
		+ "   AND seatsSold<seats"
		+ " ORDER BY departure, takeOffTime";

	resultSet = this.db.executeQuery(sql);

	while(resultSet.next()){
	    availableVols.put(new Vol(resultSet.getInt("fk_idairplane"),
		    resultSet.getString("fk_idairline"),
		    Date.valueOf(resultSet.getDate("departure").toLocalDate()),
		    resultSet.getString("destination"),
		    resultSet.getString("fk_idgeographiczone"),
		    resultSet.getInt("distance"),
		    Time.valueOf(resultSet.getTime("takeOffTime").toLocalTime()),
		    Time.valueOf(resultSet.getTime("scheduledLanding").toLocalTime()),
		    resultSet.getInt("seatsSold"),
		    resultSet.getDouble("price")),
		    resultSet.getInt("seats")
		    );
	}

	request.setAttribute("availableFlights", availableVols);
	return availableVols;
    }

    private synchronized void affichagePage(HttpServletResponse response, String message) {
	response.setContentType("text/html;charset=UTF-8");
	try (PrintWriter out = response.getWriter()) {
	    out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">\n" +
		    "<html lang=\"fr\">\n" +
		    "    <head>\n" +
		    "        <title>Caddie virtuel - INFO</title>\n" +
		    "    </head>\n" +
		    "    <body>\n" +
		    "	<h1>" + message + "</h1>"+
		    "       <form method=\"POST\" action=\"/Web_Applic_Billets/init\">\n" +
		    "          <input id=\"hiddenAction\" name=\"hiddenAction\" value=\"login\" hidden=\"true\">\n" +
		    "          <button class=\"btn btn-lg btn-primary btn-block\" type=\"submit\">retourner au début</button>\n" +
		    "       </form>"+
		    "    </body>\n" +
		    "</html>");
	}
	catch (IOException ex) {
	    this.servletContext.log("affichagePage Error", ex);
	}
    }


}
