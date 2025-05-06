package server.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.stream.Collectors;

import client.commands.utility.ConsoleInputHandler;
import collection.Dragon;
import network.models.RemoveGreaterCommandArgs;
import server.managers.ServerCommandManager;
import server.psql.auth.User;
import server.managers.DragonManager;


/**
 * Команда для удаления всех элементов коллекции, превышающих заданный по координатам.
 * Реализует интерфейс {@link Command}.
 */
public class RemoveGreaterCommand implements Command {

    private DragonManager dragonManager;

    public RemoveGreaterCommand(DragonManager dragonManager){
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
        RemoveGreaterCommandArgs args = (RemoveGreaterCommandArgs) arg;
        long x = args.x();
        long y = args.y();
        
        List<String> results = dragonManager.getSortedDragons().stream()
            .filter(dragon -> x + y > dragon.getCoordinates().getX() + dragon.getCoordinates().getY())
            .map(dragon -> {
                if (!dragonManager.preRemoveDragon(dragon, user)){
                    return "Ошибка удаления"; 
                }
                dragonManager.removeDragon(dragon);
                return String.format("Дракон с именем %s и айди %d был удалён", dragon.getName(), dragon.getId());
            })
            .collect(Collectors.toList());

        return results.isEmpty() ? "Нет драконов для удаления" : String.join("\n", results);
    }


    @Override
    public String getDescription(){
        return "удалить из коллекции все элементы, превышающие заданный по координатам";
    }


    @Override
    public String stringArgument(){
        return "{element}";
    }
    
}
