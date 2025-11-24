package si.gustavogiao.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class EncryptFile {

    public static void main(String[] args) {

        if (args.length != 5) {
            System.out.println("Usage: java -cp target/classes si.gustavogiao.crypto.EncryptFile <plainFile> <encryptedFile> <keyFile> <transformation> <ivFile>");
            System.out.println("Example: java -cp target/classes si.gustavogiao.crypto.EncryptFile message.txt message.enc chaveAES.bin \"AES/CBC/PKCS5Padding\" iv.bin");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String keyFile = args[2];
        String transformation = args[3];
        String ivFile = args[4];

        try {

            String algorithm = CryptoUtils.extractAlgorithm(transformation);
            String mode = transformation.split("/")[1];

            SecretKeySpec secretKey = CryptoUtils.loadKey(keyFile, algorithm);
            byte[] plainBytes = CryptoUtils.readFile(inputFile);

            Cipher cipher = Cipher.getInstance(transformation);

            byte[] iv;

            if (!mode.equalsIgnoreCase("ECB")) {

                iv = new byte[cipher.getBlockSize()];
                new SecureRandom().nextBytes(iv);

                IvParameterSpec ivSpec = new IvParameterSpec(iv);
                cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

                try (FileOutputStream fos = new FileOutputStream(ivFile)) {
                    fos.write(iv);
                }

            } else {
                cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            }

            byte[] cipherBytes = cipher.doFinal(plainBytes);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(cipherBytes);
            }

            System.out.println("Encryption completed.");
            System.out.println(" - Mode: " + mode);
            System.out.println(" - Input: " + inputFile);
            System.out.println(" - Output: " + outputFile);
            if (!mode.equalsIgnoreCase("ECB")) {
                System.out.println(" - IV saved to: " + ivFile);
            }

        } catch (Exception e) {
            System.out.println("Encryption error: " + e.getMessage());
        }
    }
}
