package fr.univ.amu;
import javafx.application.Application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;



public class IHM extends Application {

    private User user = new User();
    private Stage stage;
    private Scene menu;
    private RsaPublicKey actualRsaPublicKey;
    private String actualMessage;
    private Button buttonRet = new Button("Retour au menu");

    private void showMessage(String str) {
        VBox vBox = new VBox();
        Text text = new Text(str);

        vBox.setSpacing(20);
        vBox.setPadding(new Insets(20));
        vBox.getChildren().addAll(text,buttonRet);
        stage.setScene( new Scene(vBox,400,400));
    }
    private Scene askForMessage() {
        VBox vBox = new VBox();
        Text key = new Text(actualRsaPublicKey.toString());
        Text text = new Text("Entrez le message a chiffrer");
        TextField textField = new TextField();
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(20));

        Button button = new Button("Chiffrer");
        button.setOnAction(actionEvent -> {
            actualMessage = textField.getText();
            askForPathMessage(true);
        });
        vBox.getChildren().addAll(key,text,textField,button);
        return new Scene(vBox,400,400);
    }
    private void askForPathMessage(boolean isEncrypt) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        gridPane.setVgap(10);
        gridPane.setHgap(5);
        Text text;
        if (isEncrypt)
            text =new Text("Merci de spécifier le chemin ou vous voulez enregistrer.");
        else
            text = new Text("Merci de spécifier le chemin du fichier");
        gridPane.add(text,0,0);
        FileChooser fileChooser = new FileChooser();
        TextField textField = new TextField();
        gridPane.add(textField,0,2);
        Button buttonPath = new Button();

        buttonPath.setOnAction(actionEvent -> {
            textField.setText(fileChooser.showOpenDialog(new Stage()).toString());
        });
        buttonPath.setText("Chemin");
        gridPane.add(buttonPath,2,2);
        Button buttonReturn = new Button();
        if(isEncrypt)
            buttonReturn.setText("Chiffrer");
        else
            buttonReturn.setText("Déchiffrer");
        gridPane.add(buttonReturn,0,3);
        buttonReturn.setOnAction(actionEvent ->  {
            if (isEncrypt) {
                Encryptor.encryptMessageInFile(textField.getText(), actualMessage, actualRsaPublicKey);
                this.stage.setScene(this.menu);
            }
            else
                showMessage(Encryptor.decryptMessageOfFile(textField.getText(),(RsaPrivateKey) actualRsaPublicKey));

            });
        Scene scene = new Scene(gridPane,400,400);
        stage.setScene(scene);
    }
    private Scene chooseKey(boolean isEncrypt) {
        VBox vBox = new VBox();
        ObservableList<RsaPublicKey> items;

        if (isEncrypt) {
            items = FXCollections.<RsaPublicKey>observableArrayList(user.getRsaPublicKeys());
        } else
            items = FXCollections.<RsaPublicKey>observableArrayList(user.getRsaPrivateKeys());

        ListView<RsaPublicKey> listView = new ListView<>(items);
        listView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        Button button = new Button("Séléctionner");
        button.setOnAction(actionEvent -> {
            actualRsaPublicKey = listView.getSelectionModel().getSelectedItem();
            if (isEncrypt)
                stage.setScene(askForMessage());
            else
                askForPathMessage(false);
        });
        vBox.setSpacing(15);
        vBox.setPadding(new Insets(20));
        HBox hBox = new HBox();
        hBox.getChildren().addAll(button,buttonRet);
        hBox.setSpacing(20);
        vBox.getChildren().addAll(listView,hBox);
        return new Scene(vBox,400,400);
    }

    private void loadAPath(String type) {
        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(15));
        Text text = new Text("Merci de spécifier le chemin de la " + type + " : ");
        gridPane.add(text,0,0);
        FileChooser fileChooser = new FileChooser();
        TextField textField = new TextField();
        gridPane.add(textField,0,2);
        Button buttonPath = new Button();

        buttonPath.setOnAction(actionEvent -> {
            textField.setText(fileChooser.showOpenDialog(new Stage()).toString());
        });
        buttonPath.setText("Chemin");
        gridPane.add(buttonPath,2,2);
        Button buttonReturn = new Button();
        buttonReturn.setText("Charger");
        gridPane.add(buttonReturn,0,3);
        buttonReturn.setOnAction(actionEvent ->  {
            if(type.equals("publique")) {
                user.addRsaPublicKeyFromFIle(textField.getText());
                System.out.println(user.getRsaPublicKeys());
            }
            else {
                user.addRsaPrivateKeyFromFile(textField.getText());
                System.out.println(user.getRsaPrivateKeys());
            }
            this.stage.setScene(this.menu);
        });
        gridPane.setVgap(20);
        gridPane.add(buttonRet,0,4);
        Scene scene = new Scene(gridPane,400,400);
        stage.setScene(scene);
    }

    private Scene generateKey() {
        VBox vBox = new VBox();
        Text text = new Text("Entrez un nom");
        TextField textField = new TextField();

        Text text1 = new Text("Entrez une taille de clé");
        TextField textField1 = new TextField();
        Text textPaht = new Text("Où voulez vous l'enregistrer ?");
        TextField path = new TextField();
        Button button = new Button("...");
        FileChooser fileChooser = new FileChooser();
        button.setOnAction(actionEvent -> path.setText(fileChooser.showOpenDialog(new Stage()).toString()));
        Button button1 = new Button("Enregistrer");
        button1.setOnAction(actionEvent -> {
            RsaKeyGenerator rsaKeyGenerator = new RsaKeyGenerator(Integer.parseInt(textField1.getText()),textField.getText());
            user.addRsaPrivateKey(rsaKeyGenerator.getPrivateKey());
            rsaKeyGenerator.getPrivateKey().saveKey(path.getText());
            rsaKeyGenerator.getPublicKey().saveKey(path.getText());
            stage.setScene(menu);
        });
        vBox.setPadding(new Insets(10));
        vBox.setSpacing(10);
        HBox hBox = new HBox();
        hBox.setSpacing(5);
        hBox.getChildren().addAll(path,button);
        vBox.getChildren().addAll(text,textField,text1,textField1,textPaht,hBox,button1);
        return new Scene(vBox,300,300);
    }

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        GridPane gridPane = new GridPane();
        buttonRet.setOnAction(actionEvent ->
            this.stage.setScene(menu)
        );

        Text text = new Text("RSA-AP");
        text.setFont(Font.font(20));
        gridPane.add(text,0,0);
        Button buttonLoadPrivateKey = new Button();
        buttonLoadPrivateKey.setText("Charger une clé privée");
        buttonLoadPrivateKey.setOnAction(actionEvent ->  {
            loadAPath("privée");
        });
        buttonLoadPrivateKey.setMinWidth(200);
        gridPane.add(buttonLoadPrivateKey,0,1);

        Button buttonLoadPublicKey = new Button();
        buttonLoadPublicKey.setText("Charger une clé publique");
        buttonLoadPublicKey.setMinWidth(200);
        buttonLoadPublicKey.setOnAction(actionEvent -> {
           loadAPath("publique");
        });
        gridPane.add(buttonLoadPublicKey,0,2);

        Button buttonCrypt = new Button();
        buttonCrypt.setText("Chiffrer un message");
        buttonCrypt.setMinWidth(200);
        buttonCrypt.setOnAction(actionEvent -> stage.setScene(chooseKey(true)));
        gridPane.add(buttonCrypt,0,3);

        Button buttonDecrypt = new Button();
        buttonDecrypt.setText("Déchiffrer un message");
        buttonDecrypt.setMinWidth(200);
        buttonDecrypt.setOnAction(actionEvent -> stage.setScene(chooseKey(false)));
        gridPane.add(buttonDecrypt,0,4);

        Button buttonGenerateKey = new Button("Générer une clé");
        buttonGenerateKey.setMinWidth(200);
        buttonGenerateKey.setOnAction(event -> stage.setScene(generateKey()));
        gridPane.add(buttonGenerateKey,0,5);
        gridPane.setPadding(new Insets(15));
        gridPane.setHgap(5);
        gridPane.setVgap(5);


        this.menu = new Scene(gridPane,400,300);
        this.stage.setTitle("Chiffrement RSA");

        this.stage.setScene(menu);

        this.stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
