package si.gustavogiao.crypto.aes;
import javax.crypto.spec.SecretKeySpec;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CryptoUtils {

    public static String extractAlgorithm(String transformation) {
        return transformation.split("/")[0];
    }

    public static SecretKeySpec loadKey(String keyFile, String algorithm) throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get(keyFile));
        return new SecretKeySpec(keyBytes, algorithm);
    }

    /** Lê ficheiro binário */
    public static byte[] readFile(String path) throws Exception {
        return Files.readAllBytes(Paths.get(path));
    }
}
