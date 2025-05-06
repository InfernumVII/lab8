package server.commands;
import java.util.StringJoiner;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import client.commands.utility.exceptions.ArgumentNumberException;
import collection.Dragon;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.managers.exceptions.DragonFindException;
import server.psql.auth.User;

/**
 * Команда для удаления элемента коллекции по его ID.
 * Реализует интерфейс {@link Command}.
 */
public class RemoveByIdCommand implements Command {

    private DragonManager dragonManager;
    /**
     * Конструктор команды RemoveByIdCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public RemoveByIdCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует ID дракона в качестве аргумента.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду удаления дракона по его ID.
     *
     * @param arg строка, содержащая ID дракона.
     */
    @Override
    public Object execute(Object argument, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return "Ошибка авторизации";
        try {
            String arg = (String) argument;
            if (ArgHandler.checkArgForInt(arg)){
                int id = Integer.parseInt(arg);
                Dragon dragon = dragonManager.returnDragonById(id);
                if (dragon != null){
                    if (!dragonManager.preRemoveDragon(dragon, user)){
                        return "Ошибка удаления"; //Переименовать
                    }
                    dragonManager.removeDragon(dragon);
                    return "Дракон удалён.";
                }
            }
        } catch (ArgumentNumberException | DragonFindException e) {
            return e.getMessage();
        }
        return null;
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "удалить элемент из коллекции по его id";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "id";
    }
    
}
