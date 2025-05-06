package network.utility;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import collection.Dragon;

public class BytesConversions {
    public static byte[] intToBytes(int x) throws IOException { //https://stackoverflow.com/questions/2183240/java-integer-to-byte-array
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(bos);
        out.writeInt(x);
        byte[] int_bytes = bos.toByteArray();
        return int_bytes;
    }
    public static int bytesToInt(byte[] bytes) throws IOException {
        int out;
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        DataInputStream in = new DataInputStream(bis);
        out = in.readInt();
        return out;
    }
    public static byte[] objectToBytes(Object object) throws IOException{
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); // https://stackoverflow.com/questions/2836646/java-serializable-object-to-byte-array
        byte[] serialized;
        ObjectOutputStream out = new ObjectOutputStream(byteArrayOutputStream);
        out.writeObject(object);
        serialized = byteArrayOutputStream.toByteArray();
        return serialized;
    }
    public static Object bytesToObject(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = new ObjectInputStream(bis);
        Object object =  in.readObject();
        return object;
    }
    
}
