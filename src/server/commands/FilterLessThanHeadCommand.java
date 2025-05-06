package server.commands;
import java.util.StringJoiner;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import collection.Dragon;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.psql.auth.User;

/**
 * Команда для вывода элементов коллекции, значение поля head которых меньше заданного.
 * Реализует интерфейс {@link Command}.
 */
public class FilterLessThanHeadCommand implements Command {
    private DragonManager dragonManager;

    /**
     * Конструктор команды FilterLessThanHeadCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public FilterLessThanHeadCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует аргумента (количество глаз).
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду вывода элементов коллекции, значение поля head которых меньше заданного.
     *
     * @param arg аргумент команды (количество глаз).
     */
    @Override
    public Object execute(Object argument, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return "Ошибка авторизации";
        String arg = (String) argument;
        StringJoiner stringJoiner = new StringJoiner("\n");
        try {
            if (ArgHandler.checkArgForFloat(arg)){
                Float eyesCount = Float.parseFloat(arg);
                stringJoiner.add(String.format("Драконы у которых кол-во глаз на говоле меньше чем: %s", eyesCount));

                dragonManager.getSortedDragons().stream()
                                .filter(dragon -> dragon.getHead().getEyesCount() < eyesCount)
                                .map(Dragon::toString)
                                .forEachOrdered(dragon -> stringJoiner.add(dragon));
            }
            
        } catch (Exception e) {
            return e.getMessage();
        }
        return stringJoiner.toString();
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывести элементы, значение поля head которых меньше заданного";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды (количество глаз).
     */
    @Override
    public String stringArgument(){
        return "head";
    }

    
}
