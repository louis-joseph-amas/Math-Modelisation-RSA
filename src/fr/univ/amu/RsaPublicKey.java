package fr.univ.amu;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class RsaPublicKey implements Serializable {

    private String name;
    private int e,n;//public key

    RsaPublicKey(String name, int e, int n) {
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


    public int getN() {
        return n;
    }


    public static List<Integer> encode(String message) {
        List<Integer> tabChar = new ArrayList<Integer>();
        for (Character c : message.toCharArray()) {
            tabChar.add((int) c);
        }
        return tabChar;
    }
    public static String decode(List<Integer> codePointTab) {
        StringBuilder stringOut = new StringBuilder();
        for (Integer i : codePointTab) {

            stringOut.appendCodePoint(i);
        }
        return stringOut.toString();
    }

    public List<Integer> encrypt(List<Integer> codePointTab) {
        List<Integer> tabChar = new ArrayList<Integer>();
        for (Integer i : codePointTab) {
            tabChar.add(RsaMath.modularPow(i,e,n));
        }
        return tabChar;
    }
    @Override
    public String toString() {
        return "Public Key of " + name + " e= "+ e + " n= " + n;
    }
}
