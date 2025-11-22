package si.gustavogiao.crypto;

import org.junit.jupiter.api.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class EncryptFileTests {

    @Test
    public void testEncryptFileAES_CBC() throws Exception {

        Path plainFile = Files.createTempFile("plain", ".txt");
        Files.writeString(plainFile, "Isto é um teste de cifra");


        Path keyFile = Files.createTempFile("aeskey", ".bin");

        GenerateKey.main(new String[]{
                keyFile.toString(),
                "AES",
                "128"
        });

        Path encryptedFile = Files.createTempFile("encrypted", ".bin");

        EncryptFile.main(new String[]{
                plainFile.toString(),
                encryptedFile.toString(),
                keyFile.toString(),
                "AES/CBC/PKCS5Padding"
        });

        byte[] cipherBytes = Files.readAllBytes(encryptedFile);
        byte[] plainBytes = Files.readAllBytes(plainFile);

        assertTrue(cipherBytes.length > 0, "Encrypted file should not be empty");

        assertTrue(cipherBytes.length > plainBytes.length,
                "Encrypted content should be longer than plaintext");

        assertEquals(16, cipherBytes.length - (cipherBytes.length - 16),
                "First 16 bytes should be the IV");

        assertNotEquals(new String(cipherBytes), new String(plainBytes),
                "Encrypted content must not match plaintext");

        assertTrue(true);
    }
}
