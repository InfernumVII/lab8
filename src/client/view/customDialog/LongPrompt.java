package client.view.customDialog;

import java.util.ResourceBundle;

import client.internationalization.LocaleController;

public class LongPrompt extends Handler<Long> {
    private boolean allowNull;
    private long min;
    private long max;

    public LongPrompt(String prompt, boolean allowNull, long min, long max) {
        super(prompt);
        this.allowNull = allowNull;
        this.min = min;
        this.max = max;
    }

    @Override
    public boolean validateInputAndSetContent() {
        ResourceBundle cResourceBundle = LocaleController.getResourceBundle("dialog/dialog");
        final String input = getTextField().getText();
        if (input.isEmpty()) {
            if (allowNull) {
                setContent(0L);
                return true; 
            }
            printError(cResourceBundle.getString("prompt_error1")); 
            return false;
        }
        try {
            if (!input.matches("-?\\d+")){
                printError(cResourceBundle.getString("long_prompt_error1"));
                return false;
            }
            long inputParsed = Long.parseLong(input);

            if (inputParsed <= min || inputParsed > max) {
                printError(String.format(cResourceBundle.getString("long_prompt_error2"), min, max));
                return false;
            }
            setContent(inputParsed);
            return true; 
        } catch (NumberFormatException e) {
            printError(String.format(cResourceBundle.getString("long_prompt_error2"), min, max)); // TODO: localisate
            return false;
        }
    }

    
}
