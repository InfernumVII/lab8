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
public class AddIfMinCommand implements Command {
    private DragonManager dragonManager;

    public AddIfMinCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
    }

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
        dragon.setId(1);
        

        if (dragonManager.getDragonSet().isEmpty()){
            Pair<Integer,Integer> pair = dragonManager.preAddDragon(dragon, user);
            if (pair.getValue1() == -1 | pair.getValue2() == -1){
                return "Ошибка при добавлении дракона";
            }
            dragon.setId(pair.getValue1());
            dragon.setOwnerId(pair.getValue2());
            dragonManager.addDragon(dragon);
            stringJoiner.add("Новый дракон успешно добавлен.");
        } else {
            Dragon minDragon = dragonManager.getMinDragonByXAndY();
            if (dragon.getCoordinates().getX() + dragon.getCoordinates().getY() < minDragon.getCoordinates().getX() + minDragon.getCoordinates().getY()){
                Pair<Integer,Integer> pair = dragonManager.preAddDragon(dragon, user);
                if (pair.getValue1() == -1 | pair.getValue2() == -1){
                    return "Ошибка при добавлении дракона";
                }
                dragon.setId(pair.getValue1());
                dragon.setOwnerId(pair.getValue2());
                dragonManager.addDragon(dragon);
                stringJoiner.add("Новый дракон успешно добавлен.");
            } else {
                stringJoiner.add("Ваш дракон имеет большее значение, чем у минимального элемента коллекции.");
            }
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
        return "добавить новый элемент в коллекцию";
    }
    
}