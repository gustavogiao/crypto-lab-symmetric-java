package si.gustavogiao.crypto.aes;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

public class GenerateKey {

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Usage: java -cp target/classes si.gustavogiao.crypto.GenerateKey <keyfile> <algorithm> <size>");
            System.out.println("Example: java -cp target/classes si.gustavogiao.crypto.GenerateKey <keyfile> <algorithm> <size>");
            return;
        }

        String keyFile = args[0];
        String algorithm = args[1];
        int keySize;

        try {
            keySize = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.out.println("Invalid key size: must be a number.");
            return;
        }

        try {
            KeyGenerator kg = KeyGenerator.getInstance(algorithm);
            kg.init(keySize, new SecureRandom());

            SecretKey sk = kg.generateKey();
            byte[] keyBytes = sk.getEncoded();

            try (FileOutputStream fos = new FileOutputStream(keyFile)) {
                fos.write(keyBytes);
            }

            System.out.println("Key generated successfully:");
            System.out.println(" - Algorithm: " + algorithm);
            System.out.println(" - Size: " + keySize);
            System.out.println(" - Base64: " + Base64.getEncoder().encodeToString(keyBytes));
            System.out.println(" - HEX: " + HexFormat.of().formatHex(keyBytes));
            System.out.println(" - File: " + keyFile);

        } catch (Exception e) {
            System.out.println("Error generating key: " + e.getMessage());
        }
    }
}