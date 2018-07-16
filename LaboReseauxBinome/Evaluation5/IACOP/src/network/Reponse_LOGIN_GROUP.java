package network;

public class Reponse_LOGIN_GROUP {
	public static final int LOGIN_GROUP_OK = 11;
	public static final int LOGIN_GROUP_KO = 12;
	private String ip;
	private int port;
	private String errorMessage;
	private boolean successful;

	public Reponse_LOGIN_GROUP(String ip, int port) {
		this.ip = ip;
		this.port = port;
		this.errorMessage = "";
		this.successful = true;
	}

	public Reponse_LOGIN_GROUP(String errorMessage) {
		this.ip = "";
		this.port = 0;
		this.errorMessage = errorMessage;
		this.successful = false;
	}

	public String toNetworkString(String separator){
		StringBuilder builder = new StringBuilder();

		if(isSuccessful()){
			builder.append(String.valueOf(LOGIN_GROUP_OK));
			builder.append(separator);
			builder.append(this.ip);
			builder.append(separator);
			builder.append(String.valueOf(this.port));
		}
		else{
			builder.append(String.valueOf(LOGIN_GROUP_KO));
			builder.append(separator);
			builder.append(this.errorMessage);
		}

		return builder.toString();
	}

	public static Reponse_LOGIN_GROUP getInstance(String[] split){
		try{
			if(split.length == 3 && Integer.parseInt(split[0])==LOGIN_GROUP_OK){
				return new Reponse_LOGIN_GROUP(split[1], Integer.valueOf(split[2]));
			}
			else if(split.length == 2 && Integer.parseInt(split[0])==LOGIN_GROUP_KO){
				return new Reponse_LOGIN_GROUP(split[1]);
			}
			else
				return null;
		}catch(NumberFormatException ex){
			return null;
		}
	}

	//<editor-fold defaultstate="collapsed" desc="GETTER & SETTER">
	public String getIp() {
		return ip;
	}
	public int getPort() {
		return port;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public boolean isSuccessful() {
		return successful;
	}
	//</editor-fold>
}
