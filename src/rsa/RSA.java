package rsa;

import java.math.BigInteger;
import java.util.Random;

/**
 * @author Matthew Feret 
 *  Assignment: 
 *    Given public = <e, n>
 *     and private = <d, n>
 * encryption:   c = m^e mod n, given m is less than n 
 * decryption:   m = c^d mod n
 * signature:    s = m^d mod n, given m is less than n
 * verification: m = s^e mod n
 */
public class RSA {

    public static void main(String[] args) {
        System.out.println("Enter any BigInteger to be encrypted using RSA: ");
    
        BigInteger m = BigInteger.valueOf(610308699);
        RSA(m);
    }

    public static void RSA(BigInteger m) {
        System.out.println("Message to be encrypted: " + m);

        //Number of bits for random prime
        int bit = 512;

        //Create 2 randoms p & q
        Random rnd0 = new Random();
        Random rnd1 = new Random();
        BigInteger p = BigInteger.probablePrime(bit, rnd0);
        BigInteger q = BigInteger.probablePrime(bit, rnd1);
        
        //n = p * q
        BigInteger n = p.multiply(q);
        
        //phi_n = (p-1)*(q-1)
        BigInteger phi_n = (p.subtract(BigInteger.ONE))
                .multiply((q.subtract(BigInteger.ONE)));
        
        //e is relatively prime with n, the public key
        BigInteger e = generateE(BigInteger.ONE, phi_n);
        System.out.println("e: " + e);

        //d = reciprocal of e mod phi n, the private key
        BigInteger d = e.modInverse(phi_n);
        System.out.println("d: " + d);
        
        //c = encrypted message m
        BigInteger c = encrypt(m, e, n);
        System.out.println("-----------------");
        System.out.println("Encrypted input:           " + c);
        System.out.println("Decrypted message:         " + decrypt(c, d, n));
        
        BigInteger s = signature(m, d, n);
        System.out.println("Signature:                 " + s);
        System.out.println("Verification:              " + verification(s, e, n));

    }
    
//    Check for first relative prime to phi_n
    public static BigInteger generateE(BigInteger e, BigInteger phi_n) {
        BigInteger compare;

        do {
            e = e.add(BigInteger.ONE.add(BigInteger.ONE));
            compare = e.gcd(phi_n);
        } while (!compare.equals(BigInteger.ONE));

        return e;
    }

//    Encryption -- use public key
    private static BigInteger encrypt(BigInteger m, BigInteger e, BigInteger n) {
        BigInteger c = (m.modPow(e, n));

        return c;
    }
//    Decryption -- use private key
    private static BigInteger decrypt(BigInteger c, BigInteger d, BigInteger n) {
        BigInteger m = (c.modPow(d, n));

        return m;
    }
//    Signature -- (original message to the private key) mod n
    private static BigInteger signature(BigInteger m, BigInteger d, BigInteger n) {
        BigInteger s = m.modPow(d, n);

        return s;
    }
//    Verification -- returns original message
    private static BigInteger verification(BigInteger s, BigInteger e, BigInteger n) {
        BigInteger m = s.modPow(e, n);

        return m;
    }
}
