package communicator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Communicator implements Serializable {
    private DataInputStream dataInputStream;
    private DataOutputStream dataOutputStream;
    private ObjectInputStream objectInputStream;
    private ObjectOutput objectOutputStream;
    private Socket socket = null;

    public Communicator(Socket sock) throws CommunicatorException {
        System.out.println("*** Instanciating Communicator object...");

        try{
            this.socket = sock;
			OutputStream os = this.socket.getOutputStream();
			InputStream is = this.socket.getInputStream();
            dataOutputStream = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
            dataOutputStream = new DataOutputStream(os);
            dataInputStream = new DataInputStream(is);
            dataInputStream = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));

            objectOutputStream = new ObjectOutputStream(this.dataOutputStream);
            objectInputStream = new ObjectInputStream(this.dataInputStream);
        } catch (IOException ex) {
            throw new CommunicatorException("Communicator(Socket) : Erreur de création de la socket : " + ex);
        }
    }

    public void close() throws CommunicatorException{
        try{
//            objectInputStream.close();
//            objectOutputStream.flush();
//            objectOutputStream.close();

            dataInputStream.close();
            dataOutputStream.flush();
            dataOutputStream.close();
        }catch (IOException ex){
            throw new CommunicatorException("close() : Erreur de fermeture de stream(s) : " + ex);
        }
    }

    //<editor-fold defaultstate="collapsed" desc="Send methods">
	public void SendBytes(byte[] bytes) throws CommunicatorException{
		System.out.println(Arrays.toString(bytes));
		try {
			this.dataOutputStream.write(bytes);
			this.dataOutputStream.flush();
		} catch (IOException ex) {
			throw new CommunicatorException("Communicator->SendMessage() : IO Error : " + ex);
		}
	}
    public void SendMessage(String msg) throws CommunicatorException{
		String message = msg + "&FINI";
		System.out.println("Communicator->SendMessage() : Message à envoyer : " + message);

		try {
			System.out.println("Communicator->SendMessage() : Envoi d'un message...");

			dataOutputStream.writeBytes(message);
			dataOutputStream.flush();
		}
		catch (IOException e) {
			throw new CommunicatorException("Communicator->SendMessage() : IO Error : " + e);
		}

		System.out.println("Communicator->SendMessage() : Message envoyé!");
    }

    public void SendSerializable(Serializable object) throws CommunicatorException {
		if(object == null)
			throw new CommunicatorException("Communicator()->SendSerializable() : Erreur, object==null.");

		try {
			System.out.println("Communicator->SendSerializable() : Envoi d'un message...");
			objectOutputStream.writeObject(object);
			objectOutputStream.flush();
		}
		catch (IOException ex) {
			throw new CommunicatorException("Communicator()->SendSerializable() : IOException : " + ex.getMessage());
		}
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Receive methods">
	public String ReceiveMessage() throws CommunicatorException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try{
			do{
				baos.write(dataInputStream.readByte());
			}while(!baos.toString().endsWith("&FINI"));
		}
		catch(IOException e){
			throw new CommunicatorException("Communicator->ReceiveMessage() : IO Error : " + e);
		}
		System.out.println(Arrays.toString(baos.toByteArray()));
		String receivedMessage = baos.toString();
		System.out.println("Communicator->ReceiveMessage() : message récupéré : " + receivedMessage);

		receivedMessage = receivedMessage.substring(0, receivedMessage.indexOf("&FINI"));
		return receivedMessage;
    }

    public <T extends Serializable> T receiveSerializable(Class<T> genericType) throws CommunicatorException {
        try {
			System.out.println("Communicator->receiveSerializable() : récupération d'un message...");
			Object obj = objectInputStream.readObject();
				return genericType.cast(obj);
		}
		catch( ClassNotFoundException | IOException ex) {
				System.out.println("ERROR TYPE : " + ex.getClass().toString());
			throw new CommunicatorException("Communicator()->receiveSerializable() : ClassNotFoundException|IOException : " + ex.getMessage());
		}
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    public Socket getSocket() {
        return socket;
    }
    //</editor-fold>
}
