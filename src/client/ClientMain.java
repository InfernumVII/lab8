package client;

import java.io.IOException;

import client.view.login.LoginSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import shared.network.Settings;
import shared.network.exceptions.TimeOutException;
import shared.network.models.NetCommandAuth;
import shared.network.models.User;

public class ClientMain extends Application {
    private static ClientUdpNetwork client;

    @Override
    public void start(Stage primaryStage){ 
        try {
            Parent root = FXMLLoader.load(LoginSceneController.class.getResource("resources/LoginScene.fxml"));
            StackPane parent = new StackPane(root);
            Scene scene = new Scene(parent);
            primaryStage.centerOnScreen();
            //primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
        
    }

    private static void initClient(){
        Settings settings = new ClientSettings();
        try {
            client = new ClientUdpNetwork(settings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            client.sendAndGetAnswer(new NetCommandAuth("123", new User("123", "123"), new User("123", "123")));
        } catch (ClassNotFoundException | IOException | TimeOutException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
    }

    public static ClientUdpNetwork getClient(){
        return client;
    }

    public static void main(String[] args) {
        initClient();
        Application.launch(args);
    }
}
