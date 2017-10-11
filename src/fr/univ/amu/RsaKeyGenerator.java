package fr.univ.amu;

import java.math.BigInteger;



public class RsaKeyGenerator {

    private String name;
    private int size,p,q,fi,n,e,d;

    private int generateD() {
        BigInteger E = new BigInteger(""+e);
        BigInteger PHI = new BigInteger(""+fi);
        return E.modInverse(PHI).intValue();//euclidExtended(e, fi) % fi;
    }



    private int generateE() {
        int e;
        while (true) {
            e = RsaMath.randNumber(size,size+size);
            if (RsaMath.GCD(e, fi) == 1)
                break;
        }
        return e;
    }

    private int euclidExtended(int a,int b) {
        int r,u,v,r2,u2,v2,qu;
        r = a;
        u = 1;
        v = 0;
        r2 = b;
        u2 = 0;
        v2 = 1;
        qu = 0;
        while (r2 != 0) {
            qu = r / r2;
            r = r2;
            u = u2;
            v = v2;
            r2 = r - qu * r2;
            u2 = u - qu * u2;
            v2 = v - qu * v2;
        }
        return u;
     }

    public void generateKeys() {

        p = RsaMath.generateRandomPrime(size);

        while (true) {
            q = RsaMath.generateRandomPrime(size);
            if (p != q)
                break;
        }
        n = p * q;

        fi = (p - 1) * (q - 1);

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