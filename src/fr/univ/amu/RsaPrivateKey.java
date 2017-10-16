package fr.univ.amu;


import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RsaPrivateKey extends RsaPublicKey{

    private BigInteger d;//private key

    @Override
    public RsaPrivateKey loadKey(String path) {
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

    @Override
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

    public RsaPrivateKey(String name, BigInteger e, BigInteger n, BigInteger d) {
        super(name,e,n);

        this.d = d;
    }


    public BigInteger getD() {
        return d;
    }

    @Override
    public String toString() {
        return super.toString() + " and private key : d = " + d;
    }


}
