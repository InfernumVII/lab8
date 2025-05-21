package client.view.customDialog;

import java.util.ResourceBundle;

import client.internationalization.LocaleController;

public class FloatPrompt extends Handler<Float> {
    private boolean allowNull;
    private float min;
    private float max;

    public FloatPrompt(String prompt, boolean allowNull, float min, float max){
        super(prompt);
        this.allowNull = allowNull;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validateInputAndSetContent() {
        ResourceBundle cResourceBundle = LocaleController.getResourceBundle("dialog/dialog");
        final String finalInput = getTextField().getText();
        if (finalInput.isEmpty()) {
            if (allowNull) {
                setContent(0f);
                return true;
            }
            printError(cResourceBundle.getString("prompt_error1")); 
            return false;
        }
        try {
            float inputParsed = Float.parseFloat(finalInput);
            if (inputParsed <= min || inputParsed > max) {
                printError(String.format(cResourceBundle.getString("long_prompt_error2"), min, max));
                return false;
            }
            setContent(inputParsed);
            return true;
        } catch (NumberFormatException e) {
            printError(cResourceBundle.getString("float_prompt_error1"));
            return false;
        }
    }
}
