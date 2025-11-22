# Java Cryptography Suite - Instruções de Uso

Um ‘toolkit’ de criptografia simétrica implementado em Java para a unidade curricular **Segurança Informática** (3º Ano, 1º Semestre).
Este projeto foca-se na geração segura de chaves, cifra/decifra de ficheiros usando AES e suporte para transformações criptográficas definidas pelo utilizador.

---

## 📌 Overview

Este projeto explora os fundamentos da **criptografia simétrica por blocos** utilizando as bibliotecas de segurança nativas do Java (`javax.crypto`).
Inclui:

- Geração segura de chaves simétricas (AES, DES, etc.)
- Cifra de ficheiros (binário seguro)
- Decifra de ficheiros (binário seguro)
- Geração e utilização de IV (vetor de inicialização)
- Suporte para **transformações criptográficas personalizadas**, como:
    - `AES/CBC/PKCS5Padding`
    - `AES/ECB/PKCS5Padding`
    - `AES/CTR/NoPadding`
    - Entre outras suportadas pela JVM

---

## 🛠️ Tecnologias Utilizadas

- Java 23 (JDK 23)
- Maven (gestor de builds)
- Java Cryptography Architecture (JCA)
- Java Cryptography Extension (JCE)

---

## 📦 Estrutura do Projeto

```
src/
└── main/
    └── java/
        └── si/
            └── gustavogiao/
                └── crypto/
                    ├── GenerateKey.java
                    ├── EncryptFile.java
                    ├── DecryptFile.java
                    └── CryptoUtils.java
```

- `GenerateKey` → Gera uma chave simétrica
- `EncryptFile` → Cifra ficheiros
- `DecryptFile` → Decifra ficheiros
- `CryptoUtils` → Funções utilitárias comuns para cifra/decifra (carregar chave, ler ficheiros, extrair algoritmo, etc.)

---

## ⚙️ Instruções de Build

### Compilar o projeto:

```bash
mvn clean compile
```

### Executar uma classe:

```bash
java -cp target/classes si.gustavogiao.crypto.<ClassName> <args>
```

---

## 🔐 Geração de Chave

```bash
java -cp target/classes si.gustavogiao.crypto.GenerateKey <ficheiro-chave> <algoritmo> <tamanho>
```

Exemplo:

```bash
java -cp target/classes si.gustavogiao.crypto.GenerateKey chaveAES.bin AES 256
```

---

## 🔒 Cifra de Ficheiros

```bash
java -cp target/classes si.gustavogiao.crypto.EncryptFile <ficheiroClaro> <ficheiroCifrado> <chave> <transformacao>
```

Exemplo:

```bash
java -cp target/classes si.gustavogiao.crypto.EncryptFile message.txt message.enc chaveAES.bin "AES/CBC/PKCS5Padding"
```

---

## 🔓 Decifra de Ficheiros

```bash
java -cp target/classes si.gustavogiao.crypto.DecryptFile <ficheiroCifrado> <ficheiroDecifrado> <chave> <transformacao>
```

Exemplo:

```bash
java -cp target/classes si.gustavogiao.crypto.DecryptFile message.enc message_dec.txt chaveAES.bin "AES/CBC/PKCS5Padding"
```

---

## 🧪 Validação

1. Criar ficheiro:
```bash
echo "Teste de criptografia" > teste.txt
```

2. Cifrar:
```bash
java -cp target/classes si.gustavogiao.crypto.EncryptFile teste.txt teste.enc chaveAES.bin "AES/CBC/PKCS5Padding"
```

3. Decifrar:
```bash
java -cp target/classes si.gustavogiao.crypto.DecryptFile teste.enc teste_dec.txt chaveAES.bin "AES/CBC/PKCS5Padding"
```

4. Comparar:
```bash
diff teste.txt teste_dec.txt
```

---

## 📌 Requisitos Adicionais & Ambiente

### Requisitos

- Java JDK 23 ou superior
- Maven 3.9+ (opcional)

---

## 💻 Ambiente de Desenvolvimento

Desenvolvido usando:

- IntelliJ IDEA Community Edition 2025.2.5
- Maven 3.9.11
- Windows 11 (compatível com Linux e macOS)

Outras IDEs compatíveis:
- Eclipse
- NetBeans
- VS Code (Extensão Java)

---

## 🔄 Executar Sem Maven

### Compilar manualmente:

```bash
javac -d out src/main/java/si/gustavogiao/crypto/*.java
```

### Executar:

```bash
java -cp out si.gustavogiao.crypto.GenerateKey chaveAES.bin AES 256
```

---

## 🧭 Compatibilidade

- Windows 11
- Ubuntu Linux
- macOS Ventura

No Windows, se `diff` não existir:
```bash
fc teste.txt teste_dec.txt
```

---

## 📘 Contexto Académico

Projeto desenvolvido para a unidade curricular **Segurança Informática**  
3º Ano, 1º Semestre.

---

## 📄 Licença

Projeto para fins académicos e educativos.
