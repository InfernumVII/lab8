package client.view.customDialog;


public class StringPrompt extends Handler<String>{
    boolean allowNull;

    public StringPrompt(String prompt, boolean allowNull){
        super(prompt);
        this.allowNull = allowNull;
    }

    @Override
    public boolean validateInputAndSetContent() {
        final String input = getTextField().getText();
        if (!allowNull && input.isEmpty()) {
            printError("The field value cannot be empty.");
            return false;
        }
        setContent(input);
        return true;
    }



}