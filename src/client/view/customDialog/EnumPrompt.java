package client.view.customDialog;

import java.util.Arrays;

public class EnumPrompt<E extends Enum<E>> extends Handler<E> {
    private boolean allowNull;
    private E[] enums;

    public EnumPrompt(String prompt, Class<E> clazz, boolean allowNull){ //https://stackoverflow.com/questions/28907790/java-how-to-get-values-of-an-enum-class-from-an-generic-type-object-instance
        super(prompt);
        enums = clazz.getEnumConstants();
        this.allowNull = allowNull;
    }

    @Override
    public boolean validateInputAndSetContent() {
        final String input = getTextField().getText();
        if (input.isEmpty()){
            if (allowNull){
                setContent(enums[0]);
                return true;
            }
            printError("The field value cannot be empty."); 
            return false;
        }
        for (E enu : enums) {
            if (input.equalsIgnoreCase(enu.name()) || input.equals(Integer.toString(enu.ordinal() + 1))) {
                setContent(enu);
                return true;
            }
        }
        printError(String.format("The field should be one of the options: (%s)", Arrays.toString(enums))); 
        return false;
    }

}
