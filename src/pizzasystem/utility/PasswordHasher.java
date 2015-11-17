package pizzasystem.utility;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Classe responsável por processar e hashear senhas para guardar de forma
 * segura no banco de dados.
 */
public class PasswordHasher {
    private MessageDigest digester;

    /**
     * Cria um novo hasher para tratar as senhas.
     */
    public PasswordHasher() {
        try {
            digester = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e) {
            // hash won't be able to hash anything
            digester = null;
        } 
    }
    
    /**
     * Processa a senha com uma One-Way-Function e retorna o resultado codificado
     * hexadecimal. Se falhar em processar a senha, retornará o próprio argumento
     * inalterado.
     * @param plainTextPassword A senha para hashear
     * @return A senha hasheada
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
