package network.protocole.iacop.requete;

import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RequeteLoginGroup {
    public static final int LOGIN_GROUP = 10;
    private final String username;
    private final byte[] digestedPassword;
    private final long time;
    private final double rand;
    
    public RequeteLoginGroup(String username, byte[] digestedPassword, long time, double rand) {
	this.username = username;
	this.digestedPassword = digestedPassword;
	this.time = time;
	this.rand = rand;
    }
    
    public String toNetworkString(String separator){
	try {
	    StringBuilder builder = new StringBuilder();

	    builder.append(String.valueOf(LOGIN_GROUP));
	    builder.append(separator);
	    builder.append(this.username);
	    builder.append(separator);
	    builder.append(new String(this.digestedPassword, "UTF-8"));
	    builder.append(separator);
	    builder.append(String.valueOf(this.time));
	    builder.append(separator);
	    builder.append(String.valueOf(this.rand));

	    return builder.toString();
	} catch (UnsupportedEncodingException ex) {
	    Logger.getLogger(RequeteLoginGroup.class.getName()).log(Level.SEVERE, null, ex);
	}

	return null;
    }
    
    public static RequeteLoginGroup getInstance(String[] split){
	if(split.length != 5){
	    System.out.println("Split.length!=5 :" + split.length);
	    return null;
	}

	try{
	    if(Integer.parseInt(split[0]) != LOGIN_GROUP){
		System.out.println("ParseInt(split[0] != LOGIN_GROUP (" + split[0] + ")");
		return null;
	    }

	    String username = split[1];
	    byte[] digestedPassword = split[2].getBytes();
	    long time = Long.parseLong(split[3]);
	    double rand = Double.parseDouble(split[4]);

	    return new RequeteLoginGroup(username, digestedPassword, time, rand);
	}catch(NumberFormatException ex){
	    System.out.println("CATCH NumberFormatException :" + ex.getMessage());
	    return null;
	}
    }
    
    public String getUsername() {
	return username;
    }
    public byte[] getDigestedPassword() {
	return digestedPassword;
    }
    public long getTime() {
	return time;
    }
    public double getRand() {
	return rand;
    }
    
}
