package network.communication;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MulticastCommunication {
    private final MulticastSocket socket;
    private final InetAddress ip;
    private final int port;
    private final String separator;
    private final String finTrame;

    public MulticastCommunication(InetAddress locaIp, InetAddress ip, int port, String separator, String finTrame) throws IOException {
	this.ip = ip;
	this.port = port;
	this.separator = separator;
	this.finTrame = finTrame;
	this.socket = new MulticastSocket(port);
	socket.joinGroup(ip);
    }

    public void close(){
	try {
	    if(this.socket != null){
		socket.close();
		this.socket.leaveGroup(this.ip);
	    }
	} catch (IOException ex) {
	    Logger.getLogger(MulticastCommunication.class.getName()).log(Level.SEVERE, null, ex);
	}
    }
    
    public void send(Message message) throws MulticastCommunicationException{
	StringBuilder stringBuilder = new StringBuilder();

	stringBuilder.append(message.getMessageTypeInt());
	stringBuilder.append(this.separator);
	stringBuilder.append(message.getUser());
	stringBuilder.append(this.separator);
	stringBuilder.append(message.getTime());
	stringBuilder.append(this.separator);
	stringBuilder.append(message.getTag());
	stringBuilder.append(this.separator);
	stringBuilder.append(message.getMessage());
	stringBuilder.append(this.separator);
	stringBuilder.append(message.getDigest());
	stringBuilder.append(this.finTrame);

	byte[] buffer = stringBuilder.toString().getBytes();
	DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, this.ip, this.port);

	try{
		this.socket.send(datagramPacket);
		System.out.println("Message sent : " + stringBuilder.toString());
	} catch (IOException ex) {
		throw new MulticastCommunicationException("MulticastCommunicator->SendMessage() : IO Error : " + ex);
	}
    }
    
    public Message receive() throws MulticastCommunicationException{
	byte[] buffer = new byte[1000];
	DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);

	try{
		if(this.socket.isClosed())
			throw new MulticastCommunicationException("MulticastSocketCommunicator->ReceiveChatMessage() : Socket is closed");

		socket.receive(datagramPacket);
		String receivedMessage = new String(buffer).trim();
		System.out.println("Message received : " + receivedMessage);
		receivedMessage = receivedMessage.substring(0, receivedMessage.lastIndexOf(this.finTrame));

		String[] messageSplit = receivedMessage.split(this.separator);
		if(messageSplit.length != 6)
			throw new MulticastCommunicationException("MulticastSocketCommunicator->ReceiveChatMessage() : split is less than 6");


		int messageType = Integer.valueOf(messageSplit[0]);
		if(!Message.isMessageType(messageType))
			throw new MulticastCommunicationException("MulticastSocketCommunicator->ReceiveChatMessage() : split is less than 6");

		return new Message(Message.UDP.values()[messageType],
							messageSplit[1],
							messageSplit[2],
							messageSplit[3],
							messageSplit[4],
							Integer.valueOf(messageSplit[5]));

	}catch(IOException ex){
		throw new MulticastCommunicationException("MulticastCommunicator->receiveMessage() : IO Error : " + ex.getMessage());
	}catch(NumberFormatException ex){
		throw new MulticastCommunicationException("MulticastCommunicator->receiveMessage() : Failed To read message type : " + ex.getMessage());
	}
    }
    
    public MulticastSocket getSocket() {
	return socket;
    }
}
