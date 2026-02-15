package local.unp.desarrolloweb2.tienda.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public final class HashUtils {

    private static final char[] HEX = "0123456789abcdef".toCharArray();

    private HashUtils() {
    }

    public static String sha256Hex(String plainText) {
        if (plainText == null) {
            throw new IllegalArgumentException("El texto a hashear no puede ser null.");
        }

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(plainText.getBytes(StandardCharsets.UTF_8));
            char[] chars = new char[hash.length * 2];

            for (int i = 0; i < hash.length; i++) {
                int value = hash[i] & 0xFF;
                chars[i * 2] = HEX[value >>> 4];
                chars[i * 2 + 1] = HEX[value & 0x0F];
            }
            return new String(chars);
        } catch (NoSuchAlgorithmException ex) {
            throw new IllegalStateException("No se pudo inicializar el algoritmo SHA-256.", ex);
        }
    }
}
