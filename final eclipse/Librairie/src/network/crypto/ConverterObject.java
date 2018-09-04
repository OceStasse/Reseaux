package network.crypto;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public abstract class ConverterObject {
    
    public static byte[] convertObjectToByte(Object object) throws IOException {
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(object);
        return baos.toByteArray();
    }
    
    public static Object convertByteToObject(byte[] byteArray) throws IOException, ClassNotFoundException
    {
	ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return ois.readObject();
    }
    
}
