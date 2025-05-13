package client.view.login;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientMain;
import client.view.auth.AuthController;
import client.view.register.RegSceneController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import server.psql.auth.User;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;

public class LoginSceneController extends AuthController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private Text signUp;

    private Parent regScene;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            regScene = FXMLLoader.load(RegSceneController.class.getResource("resources/RegScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void signUpMouseEntered(){
        signUp.setStrokeWidth(0.1);
    }

    public void signUpMouseExited(){
        signUp.setStrokeWidth(0);
    }

    public void signUpClicked(){
        Scene currentScene = username.getScene();
        StackPane parent = (StackPane) currentScene.getRoot();
        
        regScene.translateXProperty().set(currentScene.getWidth());
        parent.getChildren().addAll(regScene);
        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(regScene.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            parent.getChildren().remove(rootPane);
        });
        timeline.play();;
    }

    public void onLoginClicked(){
        if (validateFields()){
            final User user = new User(username.getText(), password.getText());
            NetCommandAuth netCommandAuth = new NetCommandAuth("auth", user, user);
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                boolean answerB = (boolean) answer.answer();
                if (answerB == true){
                    printError("Successful authorization"); //TODO should move to the nextScene
                    AuthController.setCheckUser(user);
                } else {
                    printError("Incorrect username or password");
                }
            } catch (IOException | ClassNotFoundException | TimeOutException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
