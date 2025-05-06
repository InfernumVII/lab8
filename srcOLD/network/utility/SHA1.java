package network.utility;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Provider;
import java.security.Security;
public class SHA1 {
    public static String getSHA1String(String input, byte[] salt, String pepper){
        //https://docs.oracle.com/en/java/javase/22/docs/specs/security/standard-names.html#messagedigest-algorithms
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            byteArrayOutputStream.write(salt);
            byteArrayOutputStream.write(pepper.getBytes());
            byteArrayOutputStream.write(input.getBytes());
            byte[] hash = md.digest(byteArrayOutputStream.toByteArray());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e.getMessage());
        }
        
    }
}
