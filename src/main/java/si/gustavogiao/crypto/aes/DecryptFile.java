package si.gustavogiao.crypto.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.FileOutputStream;

public class DecryptFile {

    public static void main(String[] args) {

        if (args.length != 4) {
            System.out.println("Usage: java -cp target/classes DecryptFile <encryptedFile> <decryptedFile> <keyFile> <transformation>");
            System.out.println("Example: java -cp target/classes si.gustavogiao.crypto.DecryptFile message.enc message_dec.txt chaveAES.bin \"AES/CBC/PKCS5Padding\"");
            return;
        }

        String inputFile = args[0];
        String outputFile = args[1];
        String keyFile = args[2];
        String transformation = args[3];

        try {
            String algorithm = CryptoUtils.extractAlgorithm(transformation);
            SecretKeySpec secretKey = CryptoUtils.loadKey(keyFile, algorithm);
            byte[] encrypted = CryptoUtils.readFile(inputFile);

            Cipher cipher = Cipher.getInstance(transformation);
            int blockSize = cipher.getBlockSize();


            if (encrypted.length < blockSize) {
                System.out.println("Error: file too small to contain IV.");
                return;
            }

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

            System.out.println("Decryption completed.");
            System.out.println(" - Input: " + inputFile);
            System.out.println(" - Output: " + outputFile);
            System.out.println(" - Key: " + keyFile);

        } catch (Exception e) {
            System.out.println("Decryption error: " + e.getMessage());
        }
    }
}