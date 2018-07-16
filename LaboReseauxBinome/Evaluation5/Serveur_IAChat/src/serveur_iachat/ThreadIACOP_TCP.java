package serveur_iachat;

import communicator.Communicator;
import communicator.CommunicatorException;
import database.utilities.DatabaseAccess;
import encryption.Encrypt;
import encryption.EncryptionException;
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
import network.Reponse_LOGIN_GROUP;
import network.Requete_LOGIN_GROUP;
import server.ConsoleServeur;

public class ThreadIACOP_TCP extends Thread {
	private final ConsoleServeur guiApplication;
	private final DatabaseAccess databaseAccess;
	private final int PORT_FLY;
	private final String IP_CHAT;
	private final int PORT_CHAT;
	private final String separator;
	private Communicator communicator = null;

	private ServerSocket serverSocket = null;

	public ThreadIACOP_TCP(ConsoleServeur guiApplication, DatabaseAccess databaseAccess, int PORT_FLY, String IP_CHAT, int PORT_CHAT, String separator) {
		this.guiApplication = guiApplication;
		this.databaseAccess = databaseAccess;
		this.PORT_FLY = PORT_FLY;
		this.IP_CHAT = IP_CHAT;
		this.PORT_CHAT = PORT_CHAT;
		this.separator = separator;
	}

	@Override
	public void run() {
		this.communicator = null;
		try{
			this.serverSocket = new ServerSocket(this.PORT_FLY);
		} catch (IOException ex) {
			Logger.getLogger(ThreadIACOP_TCP.class.getName()).log(Level.SEVERE, null, ex);
			traceEvent("ServerSocket error : " + ex.getMessage());
			doStop();
		}

		//Mise en attente du serveur
		while(!isInterrupted()){
			try{
				this.communicator = new Communicator(this.serverSocket.accept());
				traceEvent("Accepted an incoming connection");

				String message = communicator.ReceiveMessage();
				String[] messageSplit = message.split(this.separator);

				Requete_LOGIN_GROUP requete = Requete_LOGIN_GROUP.getInstance(messageSplit);
				Reponse_LOGIN_GROUP reponse;
				if(requete != null){
					String errorMsg = login(requete);
					if(errorMsg.isEmpty())
						reponse = new Reponse_LOGIN_GROUP(this.IP_CHAT, this.PORT_CHAT);
					else
						reponse = new Reponse_LOGIN_GROUP(errorMsg);
				}
				else
					reponse = new Reponse_LOGIN_GROUP("Failed to parse request");

				this.communicator.SendMessage(reponse.toNetworkString(this.separator));
				this.communicator.close();
				this.communicator = null;
			}catch(IOException ex){
                System.err.println("Erreur d'accept ! ? [" + ex.getMessage() + "]");
            }catch(CommunicatorException ex){
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

	private String login(Requete_LOGIN_GROUP requete) {
		String ret = "";
		String sql = "SELECT password FROM agent WHERE login = ?";
		HashMap<Integer, Object> preparedMap = new HashMap<>();

		try{
			preparedMap.put(1, requete.getUsername());

			ResultSet resultSet = this.databaseAccess.executeQuery(sql, preparedMap);

			if(resultSet.next()){
//				if(!Arrays.equals(requete.getDigestedPassword(),
//								Encrypt.saltDigest(resultSet.getString("password"), requete.getTime(), requete.getRand()))){
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

					ResultSet res = this.databaseAccess.executeQuery(sql, preparedMap);
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
//		} catch (EncryptionException ex) {
//			ret = "Erreur Serveur (Encrypt)";
//			traceEvent("Erreur d'Encrypt : " + ex.getMessage());
		}

		return ret;
	}

	private void traceEvent(String message){
        this.guiApplication.TraceEvenements(
				this.communicator==null ? "serveur" : communicator.getSocket().getRemoteSocketAddress().toString()
				+ "#"
				+ message
				+ "#ThreadIACOP_TCP");
    }
}
