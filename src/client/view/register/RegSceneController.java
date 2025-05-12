package client.view.register;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import client.ClientMain;
import client.view.auth.AuthController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import server.psql.auth.RegistrationEnums;
import server.psql.auth.User;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;

public class RegSceneController extends AuthController implements Initializable {

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(username);
    }

    public void onSignUpClicked(){
        if (validateFields()){
            final User user = new User(username.getText(), password.getText());
            NetCommandAuth netCommandAuth = new NetCommandAuth("reg", user, user);
            try {
                Answer answer = ClientMain.getClient().sendAndGetAnswer(netCommandAuth);
                RegistrationEnums answerE = (RegistrationEnums) answer.answer();
                switch (answerE) {
                    case LOGIN_IS_EXIST:
                        printError("The user with this username already exists");
                        break;
                    case SUCCESSFUL:
                        printError("Successful registration"); //TODO move to the next Scene
                        AuthController.setCheckUser(user);
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

    


}
