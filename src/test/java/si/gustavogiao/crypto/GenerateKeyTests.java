package si.gustavogiao.crypto;

import org.junit.jupiter.api.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class GenerateKeyTests {

    @Test
    public void testGenerateAES128Key() throws Exception {
        Path keyFile = Files.createTempFile("aes128", ".bin");

        GenerateKey.main(new String[]{
                keyFile.toString(),
                "AES",
                "128"
        });

        byte[] keyBytes = Files.readAllBytes(keyFile);

        assertEquals(16, keyBytes.length, "AES-128 key must be 16 bytes");
        assertTrue(keyBytes.length > 0, "Key file should not be empty");
    }

    @Test
    public void testGenerateAES256Key() throws Exception {
        Path keyFile = Files.createTempFile("aes256", ".bin");

        GenerateKey.main(new String[]{
                keyFile.toString(),
                "AES",
                "256"
        });

        byte[] keyBytes = Files.readAllBytes(keyFile);

        assertEquals(32, keyBytes.length, "AES-256 key must be 32 bytes");
        assertTrue(keyBytes.length > 0, "Key file should not be empty");
    }

    @Test
    public void testInvalidKeySize() {
        Path keyFile;

        try {
            keyFile = Files.createTempFile("invalid", ".bin");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() ->
                GenerateKey.main(new String[]{
                        keyFile.toString(),
                        "AES",
                        "123"
                })
        );
    }

    @Test
    public void testInvalidAlgorithm() {
        Path keyFile;

        try {
            keyFile = Files.createTempFile("badalgo", ".bin");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        assertDoesNotThrow(() ->
                GenerateKey.main(new String[]{
                        keyFile.toString(),
                        "FAKE",
                        "128"
                })
        );
    }
}
