package client.view.register;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientMain;
import client.view.auth.AuthController;
import client.view.login.LoginSceneController;
import client.view.main.MainSceneController;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.util.Duration;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;
import shared.network.models.Pair;
import shared.network.models.RegistrationEnums;
import shared.network.models.User;

public class RegSceneController extends AuthController implements Initializable {
    @FXML
    private Text backButton;
    @FXML
    private AnchorPane rootPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public void switchToMainScene() {
        try {
            Scene currentScene = username.getScene();

            StackPane parent = (StackPane) currentScene.getRoot();
            Parent mainScene = FXMLLoader.load(MainSceneController.class.getResource("resources/MainWindow.fxml"));

            mainScene.translateXProperty().set(0);

            parent.getChildren().add(mainScene);
            parent.getChildren().remove(rootPane);

            currentScene.getWindow().sizeToScene();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void onSignUpClicked(){
        if (validateFields()){
            final User user = new User(username.getText(), password.getText());
            NetCommandAuth netCommandAuth = new NetCommandAuth("reg", user, user);
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                Pair<RegistrationEnums, Integer> answerP = (Pair<RegistrationEnums, Integer>) answer.answer();
                RegistrationEnums answerE = answerP.getValue1();
                switch (answerE) {
                    case LOGIN_IS_EXIST:
                        printError("The user with this username already exists");
                        break;
                    case SUCCESSFUL:
                        AuthController.setCheckUser(user);
                        AuthController.setUserId(answerP.getValue2());
                        switchToMainScene();
                        break;
                    case UNSUCCESSFUL:
                        printError("Registration error");
                }
            } catch (IOException | ClassNotFoundException | TimeOutException e){
                e.printStackTrace();
                System.exit(1);
            }
        }
    }

    public void backOnMouseEntered(){
        backButton.setStrokeWidth(0.2);
    }

    public void backOnMouseExited(){
        backButton.setStrokeWidth(0);
    }

    public void backOnMouseClicked(){
        Parent loginScene = null;
        try {
            loginScene = FXMLLoader.load(LoginSceneController.class.getResource("resources/LoginScene.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
        
        Scene currentScene = backButton.getScene();
        StackPane parent = (StackPane) currentScene.getRoot();
        
        loginScene.translateXProperty().set(-currentScene.getWidth());
        parent.getChildren().add(loginScene);

        Timeline timeline = new Timeline();
        KeyValue kv = new KeyValue(loginScene.translateXProperty(), 0, Interpolator.EASE_BOTH);
        KeyFrame kf = new KeyFrame(Duration.seconds(0.3), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            parent.getChildren().remove(rootPane);
        });
        timeline.play();
    }


}
