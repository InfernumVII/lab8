package client.commands.utility.exceptions;

public class ArgumentEnumException extends Exception {
    public ArgumentEnumException(String joinedEnums){
        super(String.format("Аргумент должен быть одним из вариантов: (%s)", joinedEnums));
    }
}
