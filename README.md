# Java Cryptography Suite - Usage Instructions

A symmetric cryptography toolkit implemented in Java for the course **Information Security** (3rd Year, 1st Semester).
This project focuses on secure key generation, file encryption/decryption using AES and IV-based schemes, and support for user-defined cryptographic transformations.

---

## Overview

This project explores the fundamentals of **block symmetric cryptography** using Java's native security libraries (`javax.crypto`).
It includes:

- Secure generation of symmetric keys (AES, DES, etc.)
- File encryption (secure binary)
- File decryption (secure binary)
- Generation and use of IV (initialization vector)
- Support for **custom cryptographic transformations**, such as:
    - `AES/CBC/PKCS5Padding`
    - `AES/ECB/PKCS5Padding`
    - `AES/CTR/NoPadding`
    - Others supported by the JVM

---

## Technologies Used

- Java 23 (JDK 23)
- Maven (build manager)
- Java Cryptography Architecture (JCA)
- Java Cryptography Extension (JCE)
- JUnit 5 (unit testing)

---

## Project Structure

```
src/
└── main/
    └── java/
        └── si/
            └── gustavogiao/
                └── crypto/
                    ├── aes/
                    │   ├── GenerateKey.java
                    │   ├── EncryptFile.java
                    │   ├── DecryptFile.java
                    │   └── CryptoUtils.java
                    └── iv/
                        ├── GenerateKey.java
                        ├── EncryptFile.java
                        ├── DecryptFile.java
                        └── CryptoUtils.java
```

- `aes/` → AES-based cryptography (examples use `message.txt`)
- `iv/` → IV-based cryptography (examples use `pic_original.bmp`)

---

## Build Instructions

### Compile the project:

```bash
mvn clean compile
```

### Run a class:

```bash
java -cp target/classes si.gustavogiao.crypto.<aes|iv>.<ClassName> <args>
```

---

## AES Example (using `message.txt`)

### Key Generation

```bash
java -cp target/classes si.gustavogiao.crypto.aes.GenerateKey keyAES.bin AES 256
```

### Encrypt File

```bash
java -cp target/classes si.gustavogiao.crypto.aes.EncryptFile message.txt message.enc keyAES.bin "AES/CBC/PKCS5Padding"
```

### Decrypt File

```bash
java -cp target/classes si.gustavogiao.crypto.aes.DecryptFile message.enc message_dec.txt keyAES.bin "AES/CBC/PKCS5Padding"
```

---

## IV Example (using `pic_original.bmp`)

### Key Generation

```bash
java -cp target/classes si.gustavogiao.crypto.iv.GenerateKey keyIV.bin AES 256
```

### Encrypt File (CBC)

```bash
java -cp target/classes si.gustavogiao.crypto.iv.EncryptFile pic_original.bmp pic_encrypted_cbc.bmp keyIV.bin "AES/CBC/PKCS5Padding" iv_cbc.bin
```

### Decrypt File (CBC)

```bash
java -cp target/classes si.gustavogiao.crypto.iv.DecryptFile pic_encrypted_cbc.bmp pic_decrypted_cbc.bmp keyIV.bin "AES/CBC/PKCS5Padding" iv_cbc.bin
```

### Encrypt File (CTR)

```bash
java -cp target/classes si.gustavogiao.crypto.iv.EncryptFile pic_original.bmp pic_encrypted_ctr.bmp keyIV.bin "AES/CTR/NoPadding" iv_ctr.bin
```

### Decrypt File (CTR)

```bash
java -cp target/classes si.gustavogiao.crypto.iv.DecryptFile pic_encrypted_ctr.bmp pic_decrypted_ctr.bmp keyIV.bin "AES/CTR/NoPadding" iv_ctr.bin
```

### Encrypt File (ECB)

```bash
java -cp target/classes si.gustavogiao.crypto.iv.EncryptFile pic_original.bmp pic_encrypted_ecb.bmp keyIV.bin "AES/ECB/PKCS5Padding" iv_ecb.bin
```

### Decrypt File (ECB)

```bash
java -cp target/classes si.gustavogiao.crypto.iv.DecryptFile pic_encrypted_ecb.bmp pic_decrypted_ecb.bmp keyIV.bin "AES/ECB/PKCS5Padding" iv_ecb.bin
```

---

## Custom Transformations

You can use any transformation supported by the JVM, e.g.:
- `AES/ECB/PKCS5Padding`
- `AES/CTR/NoPadding`
- `AES/CBC/PKCS5Padding`

Just replace the transformation string in the command examples above.

---

## JUnit 5 Unit Tests

Unit tests for key generation, encryption, and decryption are located in:
- `src/test/java/si/gustavogiao/crypto/aes/`
- `src/test/java/si/gustavogiao/crypto/iv/`

Open these folders in your IDE (IntelliJ, Eclipse, NetBeans) to view and run the tests.

---

## Requirements & Environment

- Java JDK 23 or higher
- Maven 3.9+ (optional)

---

## Manual Compilation (without Maven)

### Compile manually:

```bash
javac -d out src/main/java/si/gustavogiao/crypto/aes/*.java src/main/java/si/gustavogiao/crypto/iv/*.java
```

### Run (AES example):

```bash
java -cp out si.gustavogiao.crypto.aes.GenerateKey keyAES.bin AES 256
```

### Run (IV example):

```bash
java -cp out si.gustavogiao.crypto.iv.GenerateKey keyIV.bin AES 256
```

---

## Compatibility

- Windows 11
- Ubuntu Linux
- macOS Ventura

On Windows, if `diff` is not available:
```bash
fc message.txt message_dec.txt
fc pic_original.bmp pic_decrypted.bmp
```

---

## Academic Context

Project developed for the course **Information Security**
3rd Year, 1st Semester.

---

## License

Project for academic and educational purposes.

---

## Visualizing Encryption Modes with BMP Images

When encrypting BMP images, you can observe the effect of different cipher modes (ECB, CBC, etc.) by reconstructing the encrypted image for viewing. This is done by preserving the BMP header (first 54 bytes) and appending the encrypted pixel data.

### Step-by-Step Example

#### 1. Encrypt the BMP Image

**ECB Mode:**
```bash
java -cp target/classes si.gustavogiao.crypto.iv.EncryptFile pic_original.bmp pic_ecb.enc keyIV.bin "AES/ECB/PKCS5Padding" unused_keyIV.bin
Encryption completed.
  - Mode: ECB
  - Input: pic_original.bmp
  - Output: pic_ecb.enc
```

**CBC Mode:**
```bash
java -cp target/classes si.gustavogiao.crypto.iv.EncryptFile pic_original.bmp pic_cbc.enc keyIV.bin "AES/CBC/PKCS5Padding" iv_cbc.bin
Encryption completed.
  - Mode: CBC
  - Input: pic_original.bmp
  - Output: pic_cbc.enc
  - IV saved to: iv_cbc.bin
```

#### 2. Reconstruct the Encrypted BMP for Viewing

The BMP header is 54 bytes. Use `dd` to copy the header from the original and append the encrypted data:

**ECB:**
```bash
dd if=pic_original.bmp ibs=1 count=54 > pic_ecb.bmp
dd if=pic_ecb.enc ibs=1 skip=54 >> pic_ecb.bmp
```

**CBC:**
```bash
dd if=pic_original.bmp ibs=1 count=54 > pic_cbc.bmp
dd if=pic_cbc.enc ibs=1 skip=54 >> pic_cbc.bmp
```

- The resulting `pic_ecb.bmp` and `pic_cbc.bmp` can be opened with an image viewer to see the visual effect of each mode.
- **ECB**: Patterns from the original image are still visible.
- **CBC**: The image appears as random noise.

#### 3. Repeat for Other Modes (e.g., CTR)

**CTR Mode:**
```bash
$ java -cp target/classes si.gustavogiao.crypto.iv.EncryptFile pic_original.bmp pic_ctr.enc keyIV.bin "AES/CTR/NoPadding" iv_ctr.bin
```
```bash
dd if=pic_original.bmp ibs=1 count=54 > pic_ctr.bmp
dd if=pic_ctr.enc ibs=1 skip=54 >> pic_ctr.bmp
```

---

**Note:** This process works because the BMP header (first 54 bytes) must remain unencrypted for the file to be viewable. Only the pixel data is encrypted.

---
