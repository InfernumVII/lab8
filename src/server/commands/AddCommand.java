package server.commands;
import shared.collection.Dragon.Builder;
import shared.network.models.Pair;
import shared.network.models.User;
import server.managers.ServerCommandManager;
import server.managers.DragonManager;

import java.time.LocalDate;
import java.util.StringJoiner;


import shared.collection.Dragon;


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
        Dragon dragon = (Dragon) arg;
        dragon.setCreationDate(LocalDate.now());
        dragon.setId(1); // Q: Зачем это надо?

        stringJoiner.add("Добавление нового дракона.");
        Pair<Integer,Integer> pair = dragonManager.preAddDragon(dragon, user);
        if (pair.getValue1() == -1 | pair.getValue2() == -1){
            return "Ошибка при добавлении дракона";
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