package fr.univ.amu;

import java.io.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class Encryptor {

    public static List<BigInteger> encode(String message) {
        List<BigInteger> tabChar = new ArrayList<BigInteger>();
        for (Character c : message.toCharArray()) {
            tabChar.add(new BigInteger(String.valueOf((long) c)));
        }
        return tabChar;
    }
    public static String decode(List<BigInteger> codePointTab) {
        StringBuilder stringOut = new StringBuilder();
        try {
            for (BigInteger i : codePointTab) {
                stringOut.appendCodePoint(i.intValue());
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return "Clé invalide";
        }

        return stringOut.toString();
    }

    private static List<BigInteger> encrypt(List<BigInteger> codePointTab,RsaPublicKey rsaPublicKey) {
        List<BigInteger> tabChar = new ArrayList<BigInteger>();
        for (BigInteger i : codePointTab) {
            tabChar.add(RsaMath.modularPow(i,rsaPublicKey.getE(),rsaPublicKey.getN()));
        }
        return tabChar;
    }


    private static  List<BigInteger> decrypt(List<BigInteger> codePointTab,RsaPrivateKey rsaPrivateKey) {
        List<BigInteger> tabChar = new ArrayList<BigInteger>();
        for (BigInteger i : codePointTab) {
            tabChar.add(RsaMath.modularPow(i, rsaPrivateKey.getD(),rsaPrivateKey.getN()));
        }
        return tabChar;
    }

    private static List<BigInteger> encryptMessage(String str,RsaPublicKey rsaPublicKey) {
        List<BigInteger> list = encode(str);
        list = encrypt(list,rsaPublicKey);
        return list;
    }

    private static String decryptMessage(List<BigInteger> encryptedCodePoint,RsaPrivateKey rsaPrivateKey) {
        List<BigInteger> list = decrypt(encryptedCodePoint,rsaPrivateKey);
        return decode(list);
    }
    
    public static boolean encryptMessageInFile(String path, String message, RsaPublicKey publicKey) {
        try(FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
            objectOutputStream.writeObject(encryptMessage(message,publicKey));
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static String decryptMessageOfFile(String path, RsaPrivateKey rsaPrivateKey) {
        try(FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            return decryptMessage( (List<BigInteger>) objectInputStream.readObject(),rsaPrivateKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "Erorr";
    }

}
