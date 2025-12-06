package si.gustavogiao.crypto.iv;

import org.junit.jupiter.api.*;
import java.nio.file.*;

import static org.junit.jupiter.api.Assertions.*;

public class DecryptFileTests {

    @Test
    public void testEncryptThenDecryptAES_CBC() throws Exception {

        Path originalFile = Files.createTempFile("original", ".txt");
        String content = "Mensagem secreta para testar a decifra!";
        Files.writeString(originalFile, content);

        Path keyFile = Files.createTempFile("aeskey", ".bin");

        GenerateKey.main(new String[]{
                keyFile.toString(),
                "AES",
                "128"
        });

        Path encryptedFile = Files.createTempFile("encrypted", ".bin");

        EncryptFile.main(new String[]{
                originalFile.toString(),
                encryptedFile.toString(),
                keyFile.toString(),
                "AES/CBC/PKCS5Padding"
        });

        assertTrue(Files.size(encryptedFile) > 16,
                "Encrypted file must contain IV + ciphertext");

        Path decryptedFile = Files.createTempFile("decrypted", ".txt");

        DecryptFile.main(new String[]{
                encryptedFile.toString(),
                decryptedFile.toString(),
                keyFile.toString(),
                "AES/CBC/PKCS5Padding"
        });

        byte[] originalBytes = Files.readAllBytes(originalFile);
        byte[] decryptedBytes = Files.readAllBytes(decryptedFile);

        assertArrayEquals(
                originalBytes,
                decryptedBytes,
                "Decrypted file must match original plaintext"
        );

        assertTrue(true);
    }
}
