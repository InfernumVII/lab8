package server.commands;
import collection.Dragon.Builder;
import server.managers.ServerCommandManager;
import server.psql.auth.Pair;
import server.psql.auth.User;
import server.managers.DragonManager;

import java.time.LocalDate;
import java.util.StringJoiner;

import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.Dragon;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;


/**
 * Команда для добавления нового дракона в коллекцию.
 * Реализует интерфейс {@link Command}.
 */
public class AddCommand implements Command {
    private DragonManager dragonManager;


    public AddCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code false}, так как команда не принимает аргументов.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }


    @Override
    public Object execute(Object arg, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return "Ошибка авторизации";
        StringJoiner stringJoiner = new StringJoiner("\n");
        Builder dragonBuilder = (Builder) arg;
        stringJoiner.add("Добавление нового дракона.");
        Dragon dragon = dragonBuilder
                    .withId(1)
                    .withDate(LocalDate.now())
                    .build();
        Pair<Integer,Integer> pair = dragonManager.preAddDragon(dragon, user);
        if (pair.getValue1() == -1 | pair.getValue2() == -1){
            return "Ошибка при добавлении дракона"; //todo DRY
        }
        dragon.setId(pair.getValue1());
        dragon.setOwnerId(pair.getValue2());
        dragonManager.addDragon(dragon);
        stringJoiner.add("Новый дракон успешно добавлен.");
        return stringJoiner.toString();
        
    }

    /**
     * Возвращает описание команды.
     *
     * @return строковое описание команды.
     */
    @Override
    public String getDescription(){
        return "добавить новый элемент в коллекцию";
    }
    
}