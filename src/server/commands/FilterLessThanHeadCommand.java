package server.commands;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import shared.utility.ArgHandler;
import shared.collection.Dragon;
import shared.network.models.User;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;

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
            return null;
        String arg = (String) argument;
        try {
            if (ArgHandler.checkArgForFloat(arg)){
                Float eyesCount = Float.parseFloat(arg);

                return dragonManager.getSortedDragons().stream()
                                .filter(dragon -> dragon.getHead().getEyesCount() < eyesCount)
                                .collect(Collectors.toList());
            }
            
        } catch (Exception e) {
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
