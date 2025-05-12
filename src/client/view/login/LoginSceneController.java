package client.view.login;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LoginSceneController implements Initializable {
    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Text signUp;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(username);
    }

    public void signUpMouseEntered(){
        signUp.setStrokeWidth(0.1);
    }

    public void signUpMouseExited(){
        signUp.setStrokeWidth(0);
    }

    public void signUpClicked(){
        
    }
}
