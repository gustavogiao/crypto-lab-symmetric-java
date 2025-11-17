package si.gustavogiao.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class DecryptFile {

    public static void main(String[] args) throws Exception {

        if (args.length != 4) {
            System.out.println("Usage: java DecryptFile <encryptedFile> <decryptedFile> <keyFile> <transformation>");
            System.out.println("Example: java DecryptFile msg.enc msg_out.txt keyAES.bin \"AES/CBC/PKCS5Padding\"");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String keyFile = args[2];
        String transformation = args[3];

        String algorithm = transformation.split("/")[0];

        byte[] keyBytes = Files.readAllBytes(Paths.get(keyFile));
        SecretKeySpec secretKey = new SecretKeySpec(keyBytes, algorithm);

        byte[] encrypted = Files.readAllBytes(Paths.get(inputFile));

        Cipher cipher = Cipher.getInstance(transformation);
        int blockSize = cipher.getBlockSize();

        byte[] iv = new byte[blockSize];
        System.arraycopy(encrypted, 0, iv, 0, blockSize);

        IvParameterSpec ivSpec = new IvParameterSpec(iv);

        byte[] cipherBytes = new byte[encrypted.length - blockSize];
        System.arraycopy(encrypted, blockSize, cipherBytes, 0, cipherBytes.length);

        cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);

        byte[] plainBytes = cipher.doFinal(cipherBytes);

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            fos.write(plainBytes);
        }

        System.out.println("Decryption completed successfully.");
        System.out.println("Input file: " + inputFile);
        System.out.println("Output file: " + outputFile);
        System.out.println("Key file: " + keyFile);
    }
}
