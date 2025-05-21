package client.view.customDialog;

import java.util.ResourceBundle;

import client.internationalization.LocaleController;

public class StringPrompt extends Handler<String>{
    boolean allowNull;

    public StringPrompt(String prompt, boolean allowNull){
        super(prompt);
        this.allowNull = allowNull;
    }

    @Override
    public boolean validateInputAndSetContent() {
        ResourceBundle cResourceBundle = LocaleController.getResourceBundle("dialog/dialog");
        final String input = getTextField().getText();
        if (!allowNull && input.isEmpty()) {
            printError(cResourceBundle.getString("prompt_error1"));
            return false;
        }
        setContent(input);
        return true;
    }



}