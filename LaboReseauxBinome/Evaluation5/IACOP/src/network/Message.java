package network;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Message {
	public static enum UDP {
		POST_QUESTION(0),
		ANSWER_QUESTION(1),
		POST_EVENT(2);

		private int value;

		private UDP(int value){
			this.value = value;
		}

		private int getValue(){
			return this.value;
		}
	};

	private final String time;
	private final UDP messageType;
	private final String message;
	private final String tag;
	private final int digest;
	private final String user;

	public Message(UDP messageType, String user, String time, String tag, String message, int digest) {
		this.time = time;
		this.messageType = messageType;
		this.message = message;
		this.tag = tag;
		this.digest = digest;
		this.user = user;
	}

	public Message(UDP messageType, String user, String tag, String message, int digest) {
		DateFormat dateFormat  = new SimpleDateFormat("HH:mm:ss");
		Calendar calendar = Calendar.getInstance();

		this.time = dateFormat.format(calendar.getTime());
		this.messageType = messageType;
		this.message = message;
		this.tag = tag;
		this.digest = digest;
		this.user = user;
	}

	public static boolean isMessageType(int type){
		for(UDP udp: UDP.values()){
			if(udp.getValue() == type)
				return true;
		}
		return false;
	}

	@Override
	public String toString() {
		// message = [12:47:31][messateType][tag] laurent : voici mon message!
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("[");
		stringBuilder.append(this.time);
		stringBuilder.append("][");

		switch(this.messageType){
			case POST_QUESTION:
				stringBuilder.append("Question");
				break;
			case ANSWER_QUESTION:
				stringBuilder.append("Réponse");
				break;
			case POST_EVENT:
				stringBuilder.append("Event");
				break;
		}
		stringBuilder.append("][");
		stringBuilder.append(this.tag);
		stringBuilder.append("] ");
		stringBuilder.append(this.user);
		stringBuilder.append(" : ");
		stringBuilder.append(this.message);

		return stringBuilder.toString();
	}

	//<editor-fold defaultstate="collapsed" desc="GETTER & SETTER">
	public String getTime() {
		return time;
	}
	public UDP getMessageType() {
		return messageType;
	}
	public int getMessageTypeInt(){
		return messageType.getValue();
	}
	public String getMessage() {
		return message;
	}
	public String getTag() {
		return tag;
	}
	public int getDigest() {
		return digest;
	}
	public String getUser() {
		return user;
	}
	//</editor-fold>
}
