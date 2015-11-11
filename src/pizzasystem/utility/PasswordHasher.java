package pizzasystem.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Gabe
 */
public class PasswordHasher {
    
    private MessageDigest digester;

    /**
     *
     */
    public PasswordHasher() {
        try {
            digester = MessageDigest.getInstance("SHA-1");
        }
        catch (NoSuchAlgorithmException e) {
            // hash won't be able to hash anything
            digester = null;
        } 
    }
    
    /**
     *
     * @param plainTextPassword
     * @return
     */
    public String hash(String plainTextPassword) {
        if (digester == null)
            return plainTextPassword;
        
        byte[] bytes = digester.digest(plainTextPassword.getBytes());
        String hash = "";
        for (int i = 0; i < bytes.length; i++) {
            hash += Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1);
        }
        return hash;
    }
    
}
