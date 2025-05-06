package server.managers;


import managers.CommandManager;
import server.ServerMain;
import server.commands.*;
import server.psql.auth.Auth;
import server.psql.auth.Pair;
import server.psql.auth.User;


public class ServerCommandManager extends CommandManager<Command> {
    private static Auth authInstance = new Auth(ServerMain.getManagerInsance().getConnection());

    public void initDefaultCommands(DragonManager dragonManager){
        registerCommand("help", new HelpCommand(this));
        registerCommand("info", new InfoCommand(dragonManager));
        registerCommand("show", new ShowCommand(dragonManager));
        registerCommand("add", new AddCommand(dragonManager));
        registerCommand("update", new UpdateCommand(dragonManager));
        registerCommand("remove_by_id", new RemoveByIdCommand(dragonManager));
        registerCommand("clear", new ClearCommand(dragonManager));
        registerCommand("execute_script", new ExecuteScriptCommand());
        registerCommand("add_if_min", new AddIfMinCommand(dragonManager));
        registerCommand("remove_greater", new RemoveGreaterCommand(dragonManager));
        registerCommand("history", new HistoryCommand(this));
        registerCommand("count_by_type", new CountByTypeCommand(dragonManager));
        registerCommand("filter_by_character", new FilterByCharacterCommand(dragonManager));
        registerCommand("filter_less_than_head", new FilterLessThanHeadCommand(dragonManager));
        registerCommand("reg", new RegistrationCommand());
        registerCommand("auth", new AuthCommand());
    }

    public Object executeCommand(String name, Object args){
        Object answer;
        Command command = commands.get(name);
        Object arg = ((Pair<User, Object>) args).getValue2();
        User user = ((Pair<User, Object>) args).getValue1();
        //System.out.println(arg);
        //System.out.println(authKey);
        if (command != null) {
            if (command.isHasArgs() == true && arg == null){
                return "У команды должны быть аргументы.";
            }
            else if (command.isHasArgs() == false && arg != null){
                return "У команды не может быть аргументов.";
            } else {
                answer = command.execute(arg, user);
                storeCommand(name);
            }
            
        } else {
            return "Неизвестная команда: " + name;
        }
        return answer;
    }

    public static Auth getAuthInstance() {
        return authInstance;
    }


}
