package client.view.customDialog;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public abstract class Handler<T> {
    private T content;
    private final Text error;
    private final TextField textField;
    private final BooleanProperty validateState = new SimpleBooleanProperty(false);
    public Handler(String prompt){
        error = new Text();
        error.setStyle("-fx-fill: #dc143c;");
        textField = new TextField();
        textField.setPromptText(prompt);
        //textField.setStyle("-fx-background-color:  #37373E; -fx-text-fill:  #FFFFFF;");
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (validateInputAndSetContent()){
                validateState.set(true);
                printError("");
            } else {
                validateState.set(false);
            }
        });
    }

    public void setDefaultValue(T value){
        textField.setText(value.toString());
    }

    public BooleanProperty validateStateProperty() { return validateState; }

    public boolean getValidateStateProperty(){ return validateState.get(); }

    public abstract boolean validateInputAndSetContent();

    public T getContent() {
        return content;
    };

    protected void setContent(T content){
        this.content = content;
    }

    protected void printError(String error){
        this.error.setText(error);
    }

    protected Node[] getNodes(){
        return new Node[]{error, textField};
    }

    protected Text getError() {
        return error;
    }

    protected TextField getTextField() {
        return textField;
    }
}
