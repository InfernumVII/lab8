package client.commands.utility.exceptions;

public class ArgumentNumberException extends Exception {
    public ArgumentNumberException(){
        super("Аргумент должен быть числом.");
    }
    public ArgumentNumberException(Number min, Number max){
        super(String.format("Число должно быть между %s и %s.\n", min, max));
    }
}
