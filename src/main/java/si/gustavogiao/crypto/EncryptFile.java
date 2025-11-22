package si.gustavogiao.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.security.SecureRandom;

public class EncryptFile {

    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Usage: java -cp target/classes EncryptFile <plainFile> <encryptedFile> <keyFile> <transformation>");
            System.out.println("Example: java -cp target/classes si.gustavogiao.crypto.EncryptFile message.txt message.enc chaveAES.bin \"AES/CBC/PKCS5Padding\"");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String keyFile = args[2];
        String transformation = args[3];

        try {
            String algorithm = CryptoUtils.extractAlgorithm(transformation);
            SecretKeySpec secretKey = CryptoUtils.loadKey(keyFile, algorithm);
            byte[] plainBytes = CryptoUtils.readFile(inputFile);

            Cipher cipher = Cipher.getInstance(transformation);
            int blockSize = cipher.getBlockSize();

            byte[] iv = new byte[blockSize];
            new SecureRandom().nextBytes(iv);

            IvParameterSpec ivSpec = new IvParameterSpec(iv);

            cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);

            byte[] cipherBytes = cipher.doFinal(plainBytes);

            try (FileOutputStream fos = new FileOutputStream(outputFile)) {
                fos.write(iv);
                fos.write(cipherBytes);
            }

            System.out.println("Encryption completed.");
            System.out.println(" - Input: " + inputFile);
            System.out.println(" - Output: " + outputFile);
            System.out.println(" - Key: " + keyFile);

        } catch (Exception e) {
            System.out.println("Encryption error: " + e.getMessage());
        }
    }
}
