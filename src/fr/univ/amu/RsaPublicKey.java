package fr.univ.amu;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RsaPublicKey implements Serializable {

    private String name;
    private BigInteger e,n;//public key

    RsaPublicKey(String name, BigInteger e, BigInteger n) {
        this.name = name;
        this.e = e;
        this.n = n;
    }


    public RsaPublicKey loadKey(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (RsaPrivateKey) objectInputStream.readObject();
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    public boolean saveKey(String path) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(this);
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return false;
    }


    public BigInteger getN() {
        return n;
    }


    public List<BigInteger> encode(String message) {
        List<BigInteger> tabChar = new ArrayList<BigInteger>();
        for (Character c : message.toCharArray()) {
            tabChar.add(new BigInteger(String.valueOf((long) c)));
        }
        return tabChar;
    }
    public String decode(List<BigInteger> codePointTab) {
        StringBuilder stringOut = new StringBuilder();
        for (BigInteger i : codePointTab) {
            stringOut.appendCodePoint(i.intValue());
        }
        return stringOut.toString();
    }

    public List<BigInteger> encrypt(List<BigInteger> codePointTab) {
        List<BigInteger> tabChar = new ArrayList<BigInteger>();
        for (BigInteger i : codePointTab) {
            tabChar.add(RsaMath.modularPow(i,e,n));
        }
        return tabChar;
    }
    @Override
    public String toString() {
        return "Public Key of " + name + " e= "+ e + " n= " + n;
    }
}
