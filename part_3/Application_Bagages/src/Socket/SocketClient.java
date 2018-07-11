/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 *
 * @author jona1993
 */
public class SocketClient {
    private Socket sockCli = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private StringBuffer sendMessage = null;
    private StringBuffer receveMessage = null;
    private ObjectOutputStream oos = null;
    private ObjectInputStream ois = null;
    
    public SocketClient(String hostname,int port)throws UnknownHostException, IOException
    {
        sockCli = new Socket(hostname,port);
        dis = new DataInputStream(sockCli.getInputStream());
        dos = new DataOutputStream(sockCli.getOutputStream());
        oos = new ObjectOutputStream(sockCli.getOutputStream());
        ois = new ObjectInputStream(sockCli.getInputStream());
    }
    
    
    public void sendMessage(String Msg,char end) throws IOException, SocketClientException
    {
        if(dos == null )throw new SocketClientException("error DataOutputStream is null");
        sendMessage = new StringBuffer(Msg + end);
        dos.write(sendMessage.toString().getBytes());
        
        dos.flush();
    }
    public RequestBagage receveBagage() throws SocketClientException, IOException, ClassNotFoundException
    {
        if(ois == null)throw new SocketClientException("error ObjectOutputStream is null");
        return (RequestBagage)ois.readObject();
    }
    
    public RequestVols receveVols() throws SocketClientException, IOException, ClassNotFoundException
    {
        if(ois == null)throw new SocketClientException("error ObjectOutputStream is null");
        return (RequestVols) ois.readObject();
    }
    
    public void sendMessage(RequestDigest Msg) throws IOException, SocketClientException
    {
        if(oos == null )throw new SocketClientException("error ObjectOutputStream is null");
        oos.writeObject(Msg);
        oos.flush();
        
    }
    
    public String receveMessage(char end) throws IOException, SocketClientException
    {
        if(dis == null) throw new SocketClientException("error DataInputStream is null");
        receveMessage = new StringBuffer();
        byte b;
        while((b = dis.readByte()) != ((byte)end))
        {
            receveMessage.append((char)b);
            
        }
        return receveMessage.toString().trim();   
    }
    /**
     *
     * @throws IOException
     * @throws Throwable
     */
    @Override
    public void finalize() throws IOException, Throwable
    {
        try {
            if( oos != null)
                oos.close();
            if(ois != null)
                ois.close();
            if(dos != null)
                dos.close();
            if(dis != null)
                dis.close();
            if(sockCli != null)
                sockCli.close();
        } finally {
            super.finalize();
        }
        
    }

    public void sendMessage(String str) throws SocketClientException, IOException {
        if(dos == null )throw new SocketClientException("error DataOutputStream is null");
        dos.write(str.getBytes());
        dos.flush();
    }
    
    public RequestDigest receveRequest() throws SocketClientException, IOException, ClassNotFoundException
    {
        if(ois == null)throw new SocketClientException("error ObjectInputStream is null");
        return (RequestDigest) ois.readObject();
    }

    public String receveMessage() throws IOException {
        byte b;
        
        while((b = dis.readByte()) != (byte)'\n')
        {
            if(b != '\n')
                receveMessage.append((char)b);
        }
        return receveMessage.toString();
    }
    
}