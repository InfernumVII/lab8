package server.commands;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import shared.utility.ArgHandler;
import shared.utility.exceptions.ArgumentEnumException;
import shared.collection.Dragon;
import shared.collection.DragonCharacter;
import shared.network.models.User;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;

/**
 * Команда для вывода элементов коллекции, значение поля character которых равно заданному.
 * Реализует интерфейс {@link Command}.
 */
public class FilterByCharacterCommand implements Command {
    
    private DragonManager dragonManager;
    /**
     * Конструктор команды FilterByCharacterCommmand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public FilterByCharacterCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда требует аргумента (характер дракона).
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду вывода элементов коллекции, значение поля character которых равно заданному.
     *
     * @param arg аргумент команды (характер дракона).
     */
    @Override
    public Object execute(Object argument, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return null;
        String arg = (String) argument;
        try {
            if (ArgHandler.checkArgForEnumString(arg, DragonCharacter.values())){
                DragonCharacter dragonCharacter = DragonCharacter.valueOf(arg);

                return dragonManager.getSortedDragons().stream()
                                .filter(dragon -> dragon.getCharacter() == dragonCharacter)
                                .collect(Collectors.toList());
            }
            return null;
        } catch (ArgumentEnumException e) {
            return e.getMessage();
        }
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "вывести элементы, значение поля character которых равно заданному";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды (характер дракона).
     */
    @Override
    public String stringArgument(){
        return "character";
    }

    
}
