package server.commands;
import java.util.StringJoiner;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import client.commands.utility.exceptions.ArgumentEnumException;
import collection.Dragon;
import collection.DragonCharacter;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.psql.auth.User;

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
            return "Ошибка авторизации";
        String arg = (String) argument;
        try {
            StringJoiner stringJoiner = new StringJoiner("\n");
            if (ArgHandler.checkArgForEnumString(arg, DragonCharacter.values())){
                stringJoiner.add("Драконы с таким же характером: ");
                DragonCharacter dragonCharacter = DragonCharacter.valueOf(arg);

                dragonManager.getSortedDragons().stream()
                                .filter(dragon -> dragon.getCharacter() == dragonCharacter)
                                .map(Dragon::toString)
                                .forEachOrdered(dragon -> stringJoiner.add(dragon));
            }
            return stringJoiner.toString();
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
