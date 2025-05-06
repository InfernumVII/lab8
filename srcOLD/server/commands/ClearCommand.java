package server.commands;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.psql.auth.User;
import server.psql.exceptions.UserNotFound;


public class ClearCommand implements Command{
    private DragonManager dragonManager;

    public ClearCommand(DragonManager dragonManager) {
        this.dragonManager = dragonManager;
    }

    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(Object arg, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return "Ошибка авторизации";
        if (!dragonManager.preClearDragonSet(user))
            return "Нет драконов для очистки";
        dragonManager.clearDragonSet(user);
        return "Драконы были очищены!";
    }

    @Override
    public String getDescription(){
        return "очистить коллекцию";
    }
    
}
