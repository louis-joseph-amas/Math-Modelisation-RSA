package fr.univ.amu;

import java.math.BigInteger;



public class RsaKeyGenerator {

    private String name;

    int size;
    private BigInteger p,q,fi,n,e,d;

    private BigInteger generateD() {
        BigInteger E = new BigInteger(""+e);
        BigInteger PHI = new BigInteger(""+fi);
        return E.modInverse(PHI);//euclidExtended(e, fi) % fi;
    }



    private BigInteger generateE() {
        BigInteger e;

        while (true) {
            e = RsaMath.randNumber(size);
            if (RsaMath.GCD(e, fi).compareTo(BigInteger.ONE) == 0)
                break;
        }
        return e;
    }



    public void generateKeys() {
        p = RsaMath.generateRandomPrime(size);
        while (true) {
            q = RsaMath.generateRandomPrime(size);
            if (p != q)
                break;
        }
        n = p.multiply(q);
        fi = (p.subtract((BigInteger.ONE))).multiply (q.subtract(BigInteger.ONE));
        e = generateE();
        d = generateD();


    }


    public RsaPublicKey getPublicKey() {
        return new RsaPublicKey(name,e,n);
    }

    public RsaPrivateKey getPrivateKey() {
        return new RsaPrivateKey(name,e,n,d);
    }

    public RsaKeyGenerator(int size,String name) {
        this.size = size;
        this.name = name;
        generateKeys();
    }


    @Override
    public String toString() {
        return "Size= " + size + " " + "p= " + p + " " + "q= " + q + " " + "n= " + n + " " +
                "fi= " + fi + " " + "e= " + e + " d= " + d;
    }


}