package client.view.customDialog;

public class FloatPrompt extends Handler<Float> {
    private boolean allowNull;
    private float min;
    private float max;

    public FloatPrompt(String prompt, boolean allowNull, float min, float max){
        super(prompt);
    }

    @Override
    public boolean validateInputAndSetContent() {
        final String finalInput = getTextField().getText();
        if (finalInput.isEmpty()) {
            if (allowNull) {
                setContent(0f);
                return true;
            }
            printError("The field value cannot be empty."); 
            return false;
        }
        try {
            float inputParsed = Float.parseFloat(finalInput);

            if (inputParsed <= min || inputParsed > max) {
                printError(String.format("The number must be between %s и %s.", min, max));
                return false;
            }
            setContent(inputParsed);
            return true;
        } catch (NumberFormatException e) {
            printError("The field must be number.");
            return false;
        }
    }
}
