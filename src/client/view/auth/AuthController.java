package client.view.auth;



import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import server.psql.auth.User;

public class AuthController {
    @FXML
    protected TextField username;
    @FXML
    protected PasswordField password;
    @FXML
    protected Text error;
    @FXML
    protected Button authButton;

    private EventHandler<KeyEvent> enterEvent = event -> {
        if (event.getCode() == KeyCode.ENTER){
            authButton.fire();
        } 
    };

    private static User checkUser;
    private static int userId;

    public AuthController(){
        Platform.runLater(() -> {
            username.setOnKeyPressed(enterEvent);
            password.setOnKeyPressed(enterEvent);
        });   
    }

    public static User getCheckUser(){
        return checkUser;
    }

    public static void setCheckUser(User user){
        checkUser = user;
    }

    public static void setUserId(int id){
        userId = id;
    }

    public static int getUserId(){
        return userId;
    }

    protected void printError(String errorText){
        error.setText(errorText);
    }

    protected boolean validateFields(){
        return validateLogin() && validatePassword();
    }

    private boolean validateLogin(){
        final String usernameText = username.getText();
		if (usernameText.length() < 4){
			printError("The minimum length of the login is 4 characters");
			return false;
		} else {
			if (usernameText.length() > 20){
				printError("The maximum length of a login is 20 characters");
				return false;
			}
		}
		return true;
	}

	private boolean validatePassword(){
        final String passwordText = password.getText();
		if (passwordText.length() < 4){
			printError("The minimum password length is 4 characters");
			return false;
		} else {
			if (passwordText.length() > 100){
                printError("The maximum password length is 100 characters");
				return false;
			}
		}
		return true;
	}
}
