package fr.univ.amu;

import java.math.BigInteger;
import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        RsaKeyGenerator rsaKeyGenerator = new RsaKeyGenerator(1024,"Louis AMAS");
        RsaPrivateKey privateKey = rsaKeyGenerator.getPrivateKey();

        if (Encryptor.encryptMessageInFile("test","Jean Roger",privateKey))
            System.out.println("Fichier bien créer");
        System.out.println(Encryptor.decryptMessageOfFile("test",privateKey));
    }
}
