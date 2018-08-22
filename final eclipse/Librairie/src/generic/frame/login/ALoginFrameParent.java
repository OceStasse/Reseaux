package generic.frame.login;

import javax.swing.JFrame;

public abstract class ALoginFrameParent extends JFrame {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private String adresseIp;
    private String port;
    private String login;
    private String pwd;
    
    protected void showConnectionDialog(){
        ConnectionDialog connectionDialog = new ConnectionDialog(this);
        connectionDialog.pack();
        connectionDialog.setLocationRelativeTo(this);
        connectionDialog.setVisible(true);
    }
    
    protected abstract void connect() throws ConnectionException;
    protected abstract void disconnect() throws ConnectionException;
    
    
    /**
     * @return the adresseIp
     */
    public String getAdresseIp() {
        return adresseIp;
    }
    /**
     * @return the port
     */
    public String getPort() {
        return port;
    }
    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }
    /**
     * @return the pwd
     */
    public String getPwd() {
        return pwd;
    }
    /**
     * @param adresseIp the adresseIp to set
     */
    public void setAdresseIp(String adresseIp) {
        this.adresseIp = adresseIp;
    }
    /**
     * @param port the port to set
     */
    public void setPort(String port) {
        this.port = port;
    }
    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }
    /**
     * @param pwd the pwd to set
     */
    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

}
