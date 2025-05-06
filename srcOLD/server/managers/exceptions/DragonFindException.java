package server.managers.exceptions;

public class DragonFindException extends Exception {
    public DragonFindException(){
        super("Дракона с данным ID не найдено.");
    }
}
