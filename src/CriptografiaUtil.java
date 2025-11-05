import java.security.MessageDigest;

/**
 * Utilitário para criptografia (SHA-256).
 */
public class CriptografiaUtil {
    public static String sha256(String texto) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] out = md.digest(texto.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : out)
                sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new RuntimeException("Erro hash SHA-256", e);
        }
    }
}
