package fr.univ.amu;

import java.math.BigInteger;



public class RsaKeyGenerator {

    private String name;

    private int size;
    private BigInteger p,q,fi,n,e,d;

    private BigInteger generateD() {
        BigInteger E = new BigInteger(""+e);
        BigInteger PHI = new BigInteger(""+fi);
        return E.modInverse(PHI);//euclidExtended(e, fi) % fi;
    }
    public RsaKeyGenerator(BigInteger p, BigInteger q, BigInteger e) {
        this.p = p;
        this.q = q;
        this.e = e;
        this.name = "Hacked key";
        generateN();
        getFi();

        d = generateD();

    }


    private BigInteger generateE() {
        BigInteger e;

        while (true) {
            e = RsaMath.randNumber(p.bitLength());
            if (RsaMath.GCD(e, fi).compareTo(BigInteger.ONE) == 0 &&
                e.compareTo(fi) < 0)
                break;
        }
        return e;
    }



    public void generateKeys() {

        p = RsaMath.generateRandomPrime(size /2);
        while (true) {
            q = RsaMath.generateRandomPrime(size /2);
            if (!p.equals(q) && p.multiply(q).bitLength()  != size -1)
                break;
        }
        generateN();
        getFi();
        e = generateE();
        d = generateD();
        System.out.println("P" + p+ " Q = " + q);

    }

    private void generateN() {
        n = p.multiply(q);
    }

    private void getFi() {
        fi = (p.subtract((BigInteger.ONE))).multiply (q.subtract(BigInteger.ONE));
    }


    public RsaPublicKey getPublicKey() {
        return new RsaPublicKey(name,e,n,size);
    }

    public RsaPrivateKey getPrivateKey() {
        return new RsaPrivateKey(name,e,n,size,d);
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