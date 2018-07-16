package login;

public abstract class ParentLoginFrame extends javax.swing.JFrame {
    private String ipAddress;
    private String port;
    private String login;
    private String password;
    
    protected void showConnectionDialog(){
        ConnectionDialog connectionDialog = new ConnectionDialog(this);
        connectionDialog.pack();
        connectionDialog.setLocationRelativeTo(this);
        connectionDialog.setVisible(true);
    }
    
    protected abstract void connect() throws ConnectionException;
    protected abstract void disconnect() throws ConnectionException;

    //<editor-fold defaultstate="collapsed" desc="Getter & Setter">
    public final String getPort() {
        return port;
    }
    public final String getIpAddress() {
        return ipAddress;
    }
    public final String getPassword() {
        return password;
    }
    public final String getLogin() {
        return login;
    }

    public final void setPort(String port) {
        this.port = port;
    }
    public final void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }
    public final void setPassword(String password) {
        this.password = password;
    }
    public final void setLogin(String login) {
        this.login = login;
    }
    //</editor-fold>
}
