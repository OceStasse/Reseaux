package network.communication;

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
import java.util.Arrays;


public class Communication implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private DataInputStream dis;
    private DataOutputStream dos;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private Socket socket = null;
    
    

    public Communication(Socket socket) throws communicationException {
	System.err.println("*** Instanciating Communicator object...");

	try{
	    this.socket = socket;
	    OutputStream os = this.socket.getOutputStream();
	    InputStream is = this.socket.getInputStream();
	    dos = new DataOutputStream(new BufferedOutputStream(this.socket.getOutputStream()));
	    dos = new DataOutputStream(os);
	    dis = new DataInputStream(is);
	    dis = new DataInputStream(new BufferedInputStream(this.socket.getInputStream()));

	    oos = new ObjectOutputStream(this.dos);
	    ois = new ObjectInputStream(this.dis);
	} catch (IOException ex) {
	    throw new communicationException("Communicator(Socket) : Erreur de création de la socket : " + ex);
	}
    }


    public void send(byte[] bytes) throws communicationException{
	System.out.println(Arrays.toString(bytes));
	try {
	    this.dos.write(bytes);
	    this.dos.flush();
	} catch (IOException ex) {
	    throw new communicationException("Communicator->SendMessage() : IO Error : " + ex);
	}
    }
    
    public void send(String msg) throws communicationException{
	String message = msg + "&FINI";
	System.out.println("Communicator->SendMessage() : Message à envoyer : " + message);

	try {
	    System.out.println("Communicator->SendMessage() : Envoi d'un message...");

	    dos.writeBytes(message);
	    dos.flush();
	}
	catch (IOException e) {
	    throw new communicationException("Communicator->SendMessage() : IO Error : " + e);
	}

	System.out.println("Communicator->SendMessage() : Message envoyé!");
    }
    
    public void send(Serializable object) throws communicationException {
	if(object == null)
	    throw new communicationException("Communicator()->SendSerializable() : Erreur, object==null.");

	try {
	    System.out.println("Communicator->SendSerializable() : Envoi d'un message...");
	    oos.writeObject(object);
	    oos.flush();
	}
	catch (IOException ex) {
	    throw new communicationException("Communicator()->SendSerializable() : IOException : " + ex.getMessage());
	}
    }
    
    public String receive() throws communicationException{
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	try{
	    do{
		baos.write(dis.readByte());
	    }while(!baos.toString().endsWith("&FINI"));
	}
	catch(IOException e){
	    throw new communicationException("Communicator->ReceiveMessage() : IO Error : " + e);
	}
	System.out.println(Arrays.toString(baos.toByteArray()));
	String receivedMessage = baos.toString();
	System.out.println("Communicator->ReceiveMessage() : message récupéré : " + receivedMessage);

	receivedMessage = receivedMessage.substring(0, receivedMessage.indexOf("&FINI"));
	return receivedMessage;
    }

    public <T extends Serializable> T receive(Class<T> genericType) throws communicationException {
	try {
	    System.out.println("Communicator->receiveSerializable() : récupération d'un message...");
	    Object obj = ois.readObject();
	    return genericType.cast(obj);
	}
	catch( ClassNotFoundException | IOException ex) {
	    System.out.println("ERROR TYPE : " + ex.getClass().toString());
	    throw new communicationException("Communicator()->receiveSerializable() : ClassNotFoundException|IOException : " + ex.getMessage());
	}
    }
    
    
    /* (non-Javadoc)
     * @see java.lang.Object#finalize()
     */
    @Override
    protected void finalize() throws Throwable, communicationException {
	super.finalize();
	try{

	    dis.close();
	    dos.flush();
	    dos.close();
	    ois.close();
	    oos.flush();
	    oos.close();
	}catch (IOException ex){
	    throw new communicationException("close() : Erreur de fermeture de stream(s) : " + ex);
	}
    }


    /**
     * @return the dis
     */
    public DataInputStream getDis() {
        return dis;
    }
    /**
     * @return the dos
     */
    public DataOutputStream getDos() {
        return dos;
    }
    /**
     * @return the ois
     */
    public ObjectInputStream getOis() {
        return ois;
    }
    /**
     * @return the oos
     */
    public ObjectOutput getOos() {
        return oos;
    }
    /**
     * @return the socket
     */
    public Socket getSocket() {
        return socket;
    }
    /**
     * @param dis the dis to set
     */
    public void setDis(DataInputStream dis) {
        this.dis = dis;
    }
    /**
     * @param dos the dos to set
     */
    public void setDos(DataOutputStream dos) {
        this.dos = dos;
    }
    /**
     * @param ois the ois to set
     */
    public void setOis(ObjectInputStream ois) {
        this.ois = ois;
    }
    /**
     * @param oos the oos to set
     */
    public void setOos(ObjectOutputStream oos) {
        this.oos = oos;
    }
    /**
     * @param socket the socket to set
     */
    public void setSocket(Socket socket) {
        this.socket = socket;
    }
    
    

}
