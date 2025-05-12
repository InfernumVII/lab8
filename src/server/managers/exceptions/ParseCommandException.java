package server.managers.exceptions;

public class ParseCommandException extends Exception {
    public ParseCommandException(){
        super("У команды не может быть больше чем 1 аргумент.");
    }
}

