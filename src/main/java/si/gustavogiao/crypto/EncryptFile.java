package si.gustavogiao.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class EncryptFile {

    public static void main(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Usage: java EncryptFile <plainFile> <encryptedFile> <keyFile> <transformation>");
            System.out.println("Example: java EncryptFile msg.txt msg.enc keyAES.bin \"AES/CBC/PKCS5Padding\"");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String keyFile = args[2];
        String transformation = args[3];

        String algorithm = transformation.split("/")[0];

        byte[] keyBytes = Files.readAllBytes(Paths.get(keyFile));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, algorithm);

        byte[] plainBytes = Files.readAllBytes(Paths.get(inputFile));

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

        System.out.println("Encryption completed successfully.");
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + outputFile);
        System.out.println("Key file: " + keyFile);
    }
}
