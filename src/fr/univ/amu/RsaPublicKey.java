package fr.univ.amu;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RsaPublicKey implements Serializable {

    private String name;
    private int size;
    private BigInteger e,n;//public key

    RsaPublicKey(String name, BigInteger e, BigInteger n,int size) {
        this.name = name;
        this.e = e;
        this.n = n;
        this.size = size;
    }


    static RsaPublicKey loadKey(String path) {
        try (FileInputStream fileInputStream = new FileInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)
        ) {
            return (RsaPublicKey) objectInputStream.readObject();
        }catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return null;
    }
    public boolean saveKey(String path) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(path + ".pub");
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



    @Override
    public String toString() {
        return "Clé publique de " + name + " taille[" +size+"].";
    }

    public BigInteger getE() {
        return e;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }
}
