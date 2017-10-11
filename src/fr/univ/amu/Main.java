package fr.univ.amu;

import java.util.List;
import java.util.Map;

public class Main {

    public static void main(String[] args){

        RsaKeyGenerator gen = new RsaKeyGenerator(100,"Louis AMAS");
        RsaPrivateKey privat = gen.getPrivateKey();
        System.out.println(gen.toString());
        System.out.println(privat.toString());
        System.out.println(privat.loadKey("enzorg.rsa").toString());
    }
}
