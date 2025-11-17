package si.gustavogiao.crypto;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HexFormat;

public class GenerateKey {
    public static void main(String[] args) throws Exception {

        if(args.length != 3){
            System.out.println("Usage: java GenerateKey <key file> <algorithm> <key size>");
            System.out.println("Example: java GenerateKey chaveAES.bin AES 256");
            return;
        }

        String keyFile = args[0];
        String algorithm = args[1];
        int keySize = Integer.parseInt(args[2]);

        SecureRandom sr = new SecureRandom();

        KeyGenerator kg = KeyGenerator.getInstance(algorithm);
        kg.init(keySize, sr);

        SecretKey sk = kg.generateKey();
        byte[] keyBytes = sk.getEncoded();

        try(FileOutputStream fos = new FileOutputStream(keyFile)){
            fos.write(keyBytes);
        }

        System.out.println("Key generated successfully:");
        System.out.println("Algorithm: " + algorithm);
        System.out.println("Size: " + keySize);
        System.out.println("Base64: " + Base64.getEncoder().encodeToString(keyBytes));
        System.out.println("HEX: " + HexFormat.of().formatHex(keyBytes));
        System.out.println("File: " + keyFile);
    }
}
