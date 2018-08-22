package network.serveur.iacop;

import java.io.IOException;
import java.net.ServerSocket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import database.utilities.Access;
import generic.server.IConsoleServeur;
import network.communication.Communication;
import network.communication.communicationException;
import network.protocole.iacop.reponse.ReponseLoginGroup;
import network.protocole.iacop.requete.RequeteLoginGroup;

public class TCPThread extends Thread {
    private final IConsoleServeur guiApplication;
    private final Access db;
    private final int PORT_FLY;
    private final String IP_CHAT;
    private final int PORT_CHAT;
    private final String separator;
    private Communication c = null;

    private ServerSocket serverSocket = null;
    
    public TCPThread(IConsoleServeur guiApplication, Access db, int PORT_FLY, String IP_CHAT, int PORT_CHAT, String separator) {
	this.guiApplication = guiApplication;
	this.db = db;
	this.PORT_FLY = PORT_FLY;
	this.IP_CHAT = IP_CHAT;
	this.PORT_CHAT = PORT_CHAT;
	this.separator = separator;
    }
    
    @Override
    public void run() {
	this.c = null;
	try{
	    this.serverSocket = new ServerSocket(this.PORT_FLY);
	} catch (IOException ex) {
	    Logger.getLogger(TCPThread.class.getName()).log(Level.SEVERE, null, ex);
	    traceEvent("ServerSocket error : " + ex.getMessage());
	    doStop();
	}

	//Mise en attente du serveur
	while(!isInterrupted()){
	    try{
		this.c = new Communication(this.serverSocket.accept());
		traceEvent("Accepted an incoming connection");

		String message = c.receive();
		String[] messageSplit = message.split(this.separator);

		RequeteLoginGroup requete = RequeteLoginGroup.getInstance(messageSplit);
		ReponseLoginGroup reponse;
		if(requete != null){
		    String errorMsg = login(requete);
		    if(errorMsg.isEmpty())
			reponse = new ReponseLoginGroup(this.IP_CHAT, this.PORT_CHAT);
		    else
			reponse = new ReponseLoginGroup(errorMsg);
		}
		else
		    reponse = new ReponseLoginGroup("Failed to parse request");

		this.c.send(reponse.toNetworkString(this.separator));
		this.c = null;
	    }catch(IOException ex){
		System.err.println("Erreur d'accept ! ? [" + ex.getMessage() + "]");
	    }catch(communicationException ex){
		System.err.println("Erreur de communication ! ? [" + ex.getMessage() + "]");
	    }
	}
    }
    
    public void doStop(){
	traceEvent("Arret du serveur");
	try {
	    if(this.serverSocket != null)
		this.serverSocket.close();
	} catch (IOException ex) {
	    traceEvent(ex.getMessage());
	}

	this.interrupt();
    }
    
    private String login(RequeteLoginGroup requete) {
	String ret = "";
	String sql = "SELECT password FROM agent WHERE login = ?";
	HashMap<Integer, Object> preparedMap = new HashMap<>();

	try{
	    preparedMap.put(1, requete.getUsername());

	    ResultSet resultSet = this.db.executeQuery(sql, preparedMap);

	    if(resultSet.next()){
		if(!Arrays.equals(requete.getDigestedPassword(),resultSet.getString("password").getBytes())){
		    ret = "Wrong login/password";
		    traceEvent("Wrong login/password login:" + requete.getUsername());
		}
		else{
		    traceEvent("Login OK (login: " + requete.getUsername() + ")");
		}
	    }
	    else{
		String[] ticket = requete.getUsername().split("-");
		if(ticket.length != 3){
		    ret = "Wrong ticket number";
		    traceEvent("Wrong ticket number (number=" + requete.getUsername() + ")");
		}
		else{
		    sql = "SELECT COUNT(*)"
			    + " FROM ticket"
			    + " WHERE fk_idairplane=?"
			    + " AND fk_departure=?"
			    + " AND idticket=?";
		    preparedMap.clear();
		    preparedMap.put(1, ticket[0]);
		    preparedMap.put(2, new SimpleDateFormat("ddMMyyyy").parse(ticket[1]));
		    preparedMap.put(3, ticket[2]);

		    ResultSet res = this.db.executeQuery(sql, preparedMap);
		    res.next();

		    if(res.getInt(1)==0){
			ret = "Wrong ticket number";
			traceEvent("Wrong ticket number (number=" + requete.getUsername() + ")");
		    }
		    else{
			traceEvent("Login OK (login: " + requete.getUsername() + ")");
		    }
		}
	    }
	} catch (ParseException | SQLException ex) {
	    ret = "Erreur Serveur (SQL)";
	    traceEvent("Erreur SQL/BDD : " + ex.getMessage());
	}

	return ret;
    }

    private void traceEvent(String message){
	this.guiApplication.TraceEvenements(
		this.c==null ? "serveur" : c.getSocket().getRemoteSocketAddress().toString()
			+ "#"
			+ message
			+ "#ThreadIACOP_TCP");
    }

}
