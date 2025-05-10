package client;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import client.view.login.LoginSceneController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import shared.Some;

public class ClientMain extends Application {

    @Override
    public void start(Stage primaryStage){ 
        try {
            Parent root = FXMLLoader.load(LoginSceneController.class.getResource("resources/LoginScene.fxml"));
            Scene scene = new Scene(root);
            primaryStage.centerOnScreen();
            primaryStage.setResizable(false);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public static void main(String[] args) {
        
        Application.launch(args);
    }
}
