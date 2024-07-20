package thesis.zayarlinnhtet.volt_hunger;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;

public class RSA {
    public static BigInteger encrypt(BigInteger message, BigInteger e, BigInteger n) {
        return message.modPow(e, n);
    }

    public static BigInteger decrypt(BigInteger encrypted, BigInteger d, BigInteger n) {
        return encrypted.modPow(d, n);
    }

    public static void main(String[] args) {
        RSAKeyGenerator keyGen = new RSAKeyGenerator();

        BigInteger publicKey = keyGen.getPublicKey();
        BigInteger privateKey = keyGen.getPrivateKey();
        BigInteger modulus = keyGen.getModulus();

        String message = "\"On ~     the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"";
        
        byte[] messageBytes = message.getBytes(StandardCharsets.UTF_8);
        int chunkSize = modulus.bitLength() / 8 - 1;
        int numChunks = (int) Math.ceil((double) messageBytes.length / chunkSize);

        BigInteger[] encryptedChunks = new BigInteger[numChunks];
        BigInteger[] decryptedChunks = new BigInteger[numChunks];

        // Encrypt each chunk
        for (int i = 0; i < numChunks; i++) {
            int start = i * chunkSize;
            int length = Math.min(chunkSize, messageBytes.length - start);

            byte[] chunk = new byte[length];
            System.arraycopy(messageBytes, start, chunk, 0, length);

            BigInteger chunkBigInt = new BigInteger(1, chunk);
            encryptedChunks[i] = encrypt(chunkBigInt, publicKey, modulus);
        }

        // Decrypt each chunk
        for (int i = 0; i < numChunks; i++) {
            decryptedChunks[i] = decrypt(encryptedChunks[i], privateKey, modulus);
        }

        // Combine decrypted chunks into a single message
        byte[] decryptedMessageBytes = new byte[messageBytes.length];
        int pos = 0;
        for (int i = 0; i < numChunks; i++) {
            byte[] chunkBytes = decryptedChunks[i].toByteArray();
            System.arraycopy(chunkBytes, 0, decryptedMessageBytes, pos, chunkBytes.length);
            pos += chunkBytes.length;
        }

        String decryptedMessage = new String(decryptedMessageBytes, StandardCharsets.UTF_8);
        System.out.println("Decrypted Message: " + decryptedMessage);
    }
}
