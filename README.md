# Java Cryptography Suite

A symmetric cryptography toolkit implemented in Java for the university course **Segurança Informática** (3rd Year, 1st Semester).  
This project focuses on secure key generation, AES file encryption/decryption, and support for user-defined cryptographic transformations.

---

## 📌 Overview

This project explores the fundamentals of **symmetric block cipher cryptography** using Java’s built-in security libraries (`javax.crypto`).  
It includes:

- Secure generation of symmetric keys (AES, DES, etc.)
- Binary-safe file encryption
- Binary-safe file decryption
- IV generation and handling (CBC mode)
- Support for **arbitrary cryptographic transformations**, such as:
    - `AES/CBC/PKCS5Padding`
    - `AES/ECB/PKCS5Padding`
    - `AES/CTR/NoPadding`
    - and others supported by the JVM

The implementation follows the requirements defined in the official practical assignment for the course.

---

## 🛠️ Technologies Used

- **Java 23 (JDK 23)**
- **Maven** (project management & build)
- Java Cryptography Architecture (JCA)
- Java Cryptography Extension (JCE)

---

## 📦 Project Structure

```
src/
└── main/
    └── java/
        └── si/
            └── gustavogiao/
                └── crypto/
                    ├── GenerateKey.java
                    ├── EncryptFile.java
                    └── DecryptFile.java
```


- `GenerateKey` → Generates a symmetric key using the chosen algorithm and key size
- `EncryptFile` → Encrypts a file (binary-safe)
- `DecryptFile` → Decrypts a previously encrypted file

---

## ⚙️ Build Instructions

### Compile the project:

```bash
mvn clean compile
```
### Run a spefic tool:
```bash
java -cp target/classes si.gustavogiao.crypto.<ClassName> <args>
```

### 🔐 Key Generation

Generates a symmetric key using the algorithm and size chosen by the user.
```bash
java -cp target/classes si.gustavogiao.crypto.GenerateKey <keyfile> <algorithm> <size>
```

Example
```bash
java -cp target/classes si.gustavogiao.crypto.GenerateKey chaveAES.bin AES 256
```
This creates a 256-bit AES key stored in chaveAES.bin (binary format).

### 🔒 File Encryption
Encrypts a file using the specified key and transformation.
```bash
java -cp target/classes si.gustavogiao.crypto.EncryptFile <inputFile> <outputFile> <keyFile> <transformation>
```
Example
```bash
java -cp target/classes si.gustavogiao.crypto.EncryptFile message.txt message.enc chaveAES.bin "AES/CBC/PKCS5Padding"
```
- A new IV is generated automatically
- The output file begins with the IV (first 16 bytes)
- The remaining bytes are the encrypted ciphertext

### 🔓 File Decryption
Decrypts a file using the specified key and transformation.
```bash
java -cp target/classes si.gustavogiao.crypto.DecryptFile <encryptedFile> <outputFile> <keyFile> <transformation>
```
Example
```bash
java -cp target/classes si.gustavogiao.crypto.DecryptFile message.enc message_decrypted.txt chaveAES.bin "AES/CBC/PKCS5Padding"
```
The program automatically:
- Reads the first 16 bytes as the IV
- Uses the rest of the file as ciphertext
- Restores the original plaintext

### 🧪 Validation & Testing
To verify correctness:
1. Create a test file: 
```bash
echo "This is a cryptography test." > test.txt
```
2. Encrypt the file:
```bash
java -cp target/classes si.gustavogiao.crypto.EncryptFile test.txt test.enc chaveAES.bin "AES/CBC/PKCS5Padding"
```
3. Decrypt the file:
```bash
java -cp target/classes si.gustavogiao.crypto.DecryptFile test.enc test_dec.txt chaveAES.bin "AES/CBC/PKCS5Padding"
```
4. Compare original and decrypted files:
```bash
diff test.txt test_dec.txt
```
Files should be identical.

### 📘 Academic Context

This project was developed as part of the practical assignment for the course:
Segurança Informática (Information Security)
University — 3rd Year, 1st Semester
It demonstrates the practical application of symmetric cryptography concepts using Java’s JCA/JCE frameworks.

### 📄 License

This project is intended for academic and educational purposes.