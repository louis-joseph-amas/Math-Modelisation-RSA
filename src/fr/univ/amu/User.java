package fr.univ.amu;

import java.util.ArrayList;
import java.util.List;

import java.util.Scanner;

public class User {

    private List<RsaPublicKey> rsaPublicKeys = new ArrayList<>();

    private List<RsaPublicKey> rsaPrivateKeys = new ArrayList<>();

    public void addRsaPrivateKeyFromFile(String path) {
        RsaPrivateKey rsaPrivateKey = RsaPrivateKey.loadKey(path);
        rsaPublicKeys.add(rsaPrivateKey);
        rsaPrivateKeys.add(rsaPrivateKey);
    }
    public void addRsaPrivateKey(RsaPrivateKey rsaPrivateKey) {
        rsaPrivateKeys.add(rsaPrivateKey);
        RsaPublicKey rsaPublicKey = (RsaPublicKey) rsaPrivateKey;
        rsaPublicKeys.add(rsaPublicKey);
    }
    public void addRsaPublicKeyFromFIle(String path) {
        RsaPublicKey rsaPublicKey = RsaPublicKey.loadKey(path);
        rsaPublicKeys.add(rsaPublicKey);
    }

    public List<RsaPublicKey> getRsaPublicKeys() {
        return rsaPublicKeys;
    }

    public static String getMessage() {
        System.out.println("Entrez le message a chiffrer: ");
        return askForString();
    }
    public static int userInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextInt();
    }
    public static boolean askToSave() {
        System.out.println("Voulez vous sauvegarder ? oui ou n'importe quoi");
        String response = askForString();
        return response.equals("oui");

    }
    public static String askForPath() {
        System.out.println("Entrée le chemin du fichier : ");
        return askForString();
    }


    public static String askForString() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }

    public List<RsaPublicKey> getRsaPrivateKeys() {
        return rsaPrivateKeys;
    }
}
