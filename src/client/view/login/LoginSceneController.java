package client.view.login;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

public class LoginSceneController implements Initializable {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text signUp;
    @FXML
    private Text error;

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
        KeyFrame kf = new KeyFrame(Duration.seconds(0.1), kv);
        timeline.getKeyFrames().add(kf);
        timeline.setOnFinished(event -> {
            parent.getChildren().remove(rootPane);
        });
        timeline.play();;
    }
}
