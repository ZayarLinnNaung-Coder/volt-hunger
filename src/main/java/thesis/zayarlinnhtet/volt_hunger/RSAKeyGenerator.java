package thesis.zayarlinnhtet.volt_hunger;

import java.math.BigInteger;
import java.security.SecureRandom;

public class RSAKeyGenerator {
    private BigInteger p, q, n, phi, e, d;
    private int bitLength = 1024;
    private SecureRandom random = new SecureRandom();

    public RSAKeyGenerator() {
        p = BigInteger.probablePrime(bitLength / 2, random);
        q = BigInteger.probablePrime(bitLength / 2, random);
        n = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.valueOf(65537); // Common choice for e

        // Calculate d
        d = e.modInverse(phi);
    }

    public BigInteger getPublicKey() {
        return e;
    }

    public BigInteger getPrivateKey() {
        return d;
    }

    public BigInteger getModulus() {
        return n;
    }

    public static void main(String[] args) {
        RSAKeyGenerator keyGen = new RSAKeyGenerator();
        System.out.println("Public Key (e, n): " + keyGen.getPublicKey() + ", " + keyGen.getModulus());
        System.out.println("Private Key (d, n): " + keyGen.getPrivateKey() + ", " + keyGen.getModulus());
    }
}
