package server.commands;

import java.util.StringJoiner;

import client.commands.utility.ConsoleInputHandler;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.psql.auth.User;

/**
 * Команда для вывода информации о коллекции.
 * Реализует интерфейс {@link Command}.
 */
public class InfoCommand implements Command {
    private DragonManager dragonManager;
    /**
     * Конструктор команды InfoCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public InfoCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code false}, так как команда не принимает аргументов.
     */
    @Override
    public boolean isHasArgs(){
        return false;
    }

    /**
     * Выполняет команду вывода информации о коллекции.
     *
     * @param arg аргумент команды (в данной команде не используется).
     */
    @Override
    public Object execute(Object arg, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return "Ошибка авторизации";
        StringJoiner stringJoiner = new StringJoiner("\n");
        stringJoiner.add("Тип коллекции: " + dragonManager.getTypeName());
        stringJoiner.add("Дата инициализации: " + dragonManager.getInitializationDate());
        stringJoiner.add("Количество элементов: " + dragonManager.getDragonSet().size());
        return stringJoiner.toString();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывод информации о коллекции.";
    }
    
}
