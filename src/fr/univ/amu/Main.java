package fr.univ.amu;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){
        RsaKeyGenerator rsaKeyGenerator = new RsaKeyGenerator(15,"Louis AMAS");
        RsaPrivateKey privateKey = rsaKeyGenerator.getPrivateKey();
        System.out.println(rsaKeyGenerator);
        System.out.println(privateKey.decode(privateKey.decrypt(privateKey.encrypt(privateKey.encode("Salut")))));
    }
}
