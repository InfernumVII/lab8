package client.view.customDialog;
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
        final String input = getTextField().getText();
        if (input.isEmpty()) {
            if (allowNull) {
                setContent(0L);
                return true; 
            }
            printError("The field value cannot be empty."); 
            return false;
        }
        try {
            if (!input.matches("-?\\d+")){
                printError("The field must be an integer number.");
                return false;
            }
            long inputParsed = Long.parseLong(input);

            if (inputParsed <= min || inputParsed > max) {
                printError(String.format("The number must be between %s and %s.", min, max));
                return false;
            }
            setContent(inputParsed);
            return true; 
        } catch (NumberFormatException e) {
            printError(String.format("The number must be between %s and %s.", min, max));
            return false;
        }
    }

    
}
