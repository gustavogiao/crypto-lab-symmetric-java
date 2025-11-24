package si.gustavogiao.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;

public class DecryptFile {

    public static void main(String[] args) {

        if (args.length != 5) {
            System.out.println("Usage: java -cp target/classes si.gustavogiao.crypto.DecryptFile <encryptedFile> <decryptedFile> <keyFile> <transformation> <ivFile>");
            System.out.println("Example: java -cp target/classes si.gustavogiao.crypto.DecryptFile message.enc message_dec.txt chaveAES.bin \"AES/CBC/PKCS5Padding\" iv.bin");
            return;
        }

        String encryptedFile = args[0];
        String outputFile = args[1];
        String keyFile = args[2];
        String transformation = args[3];
        String ivFile = args[4];

        try {
            String algorithm = CryptoUtils.extractAlgorithm(transformation);
            String mode = transformation.split("/")[1];

            SecretKeySpec secretKey = CryptoUtils.loadKey(keyFile, algorithm);
            byte[] encryptedBytes = CryptoUtils.readFile(encryptedFile);

            Cipher cipher = Cipher.getInstance(transformation);

            if (!mode.equalsIgnoreCase("ECB")) {

                byte[] iv = CryptoUtils.readFile(ivFile);
                IvParameterSpec ivSpec = new IvParameterSpec(iv);

                cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

            } else {
                cipher.init(Cipher.DECRYPT_MODE, secretKey);
            }

            byte[] plainBytes = cipher.doFinal(encryptedBytes);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(plainBytes);
            }

            System.out.println("Decryption completed.");
            System.out.println(" - Mode: " + mode);
            System.out.println(" - Input: " + encryptedFile);
            System.out.println(" - Output: " + outputFile);
            if (!mode.equalsIgnoreCase("ECB"))
                System.out.println(" - IV used from: " + ivFile);

        } catch (Exception e) {
            System.out.println("Decryption error: " + e.getMessage());
        }
    }
}
