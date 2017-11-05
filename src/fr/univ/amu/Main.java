package fr.univ.amu;

import java.util.List;



public class Main {

    private static RsaPublicKey chooseFromExistingKey(List<RsaPublicKey> rsaPublicKeys) {


        if (rsaPublicKeys.isEmpty())
            return null;
        System.out.println(rsaPublicKeys);
        int i;
        do {
            System.out.println("S'il vous plait entrée un numéro de clé entre 0 et " + (rsaPublicKeys.size()- 1));
            i = User.userInput();
            if (i >= 0 && i <= rsaPublicKeys.size() - 1)
                return rsaPublicKeys.get(i);
        } while (true);

    }

    public static void main(String[] args){

        User user = new User();
        String str = "";
        do {
            System.out.println("-----------[ MENU ]---------------\n" +
                               "| [0] Charger une clé privée     |\n" +
                               "| [1] Charger une clé publique   |\n"+
                               "| [2] Chiffrer un message        |\n" +
                               "| [3] Déchiffrer un message      |\n" +
                               "| [4] Générer des clés           |\n" +
                               "| [5] Afficher les clés chargées |\n" +
                               "| [6] Cracker une clé            |\n" +
                               "----------------------------------");
            int i = User.userInput();
            switch (i) {
                case 0:
                    user.addRsaPrivateKeyFromFile(User.askForPath());
                    System.out.println("Clé privée bien chargée");
                    break;
                case 1:
                    user.addRsaPublicKeyFromFIle(User.askForPath());
                    System.out.println("Clé publique bien chargée");
                    break;
                case 2:
                    RsaPublicKey rsaPublicKey = chooseFromExistingKey(user.getRsaPublicKeys());
                    System.out.println("Ou voulez vous le chiffrer");
                    Encryptor.encryptMessageInFile(User.askForPath(),user.getMessage(),rsaPublicKey);
                    break;
                case 3:
                    RsaPrivateKey rsaPrivateKey = (RsaPrivateKey) chooseFromExistingKey(user.getRsaPrivateKeys());
                    String path = User.askForPath();
                    System.out.println(Encryptor.decryptMessageOfFile(path,rsaPrivateKey));
                    break;
                case 4:
                    System.out.println("Choisir une taille en bit: ");
                    int size = User.userInput();
                    System.out.println("Demander un nom :");
                    String nom = User.askForString();
                    RsaKeyGenerator rsaKeyGenerator = new RsaKeyGenerator(size,nom);
                    user.addRsaPrivateKey(rsaKeyGenerator.getPrivateKey());
                    if (User.askToSave()) {
                        String pathPublic;
                        String pathPrivate;
                        System.out.println("Pour la clé publique");
                        pathPublic = User.askForPath();
                        rsaKeyGenerator.getPublicKey().saveKey(pathPublic);
                        System.out.println("Pour la clé privée");
                        pathPrivate = User.askForPath();
                        rsaKeyGenerator.getPrivateKey().saveKey(pathPrivate);
                        System.out.println("Clé bien enregistrée");
                    }
                    break;
                case 5:
                    System.out.println("Les clés publiques chargées sont \n" + user.getRsaPublicKeys());
                    System.out.println("Les clés privées chargées sont \n" + user.getRsaPrivateKeys());
                    break;
                case 6:
                    System.out.println("Choisir une clé publique a craquée");
                    user.addRsaPrivateKey(RsaMath.crack(chooseFromExistingKey(user.getRsaPublicKeys())));

                    break;
                default:
                    str = "stop";
                    break;
            }
        } while ((!str.equals("stop")));
    }
}
