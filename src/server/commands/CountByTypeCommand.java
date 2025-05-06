package server.commands;
import java.util.StringJoiner;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import collection.Dragon;
import collection.DragonType;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.psql.auth.User;

/**
 * Команда для подсчета количества драконов определенного типа в коллекции.
 * Реализует интерфейс {@link Command}.
 */
public class CountByTypeCommand implements Command {

    
    private DragonManager dragonManager;
    /**
     * Конструктор команды CountByTypeCommand.
     *
     * @param dragonManager объект {@link DragonManager} для управления коллекцией драконов.
     */
    public CountByTypeCommand(DragonManager dragonManager){
        this.dragonManager = dragonManager;
    }

    /**
     * Проверяет, имеет ли команда аргументы.
     *
     * @return возвращает {@code true}, так как команда тип дракона в виде аргумента.
     */
    @Override
    public boolean isHasArgs(){
        return true;
    }

    /**
     * Выполняет команду, подсчитывая количество драконов заданного типа.
     *
     * @param arg аргумент команды (тип дракона).
     */
    @Override
    public Object execute(Object argument, User user){
        if (!ServerCommandManager.getAuthInstance().checkUserCreds(user))
            return "Ошибка авторизации";
        try {
            String arg = (String) argument;
            StringJoiner stringJoiner = new StringJoiner("\n");
            if (ArgHandler.checkArgForEnumString(arg, DragonType.values())){
                DragonType dragonType = DragonType.valueOf(arg);

                long count = dragonManager.getDragonSet().stream()
                                .filter(dragon -> dragon.getType() == dragonType)
                                .count();
                
                stringJoiner.add(String.format("Количество драконов с данным типом: %d", count));
            }
            return stringJoiner.toString();
        } catch (Exception e) {
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
        return "вывести количество элементов, значение поля type которых равно заданному";
    }

    /**
     * Возвращает строковое представление аргумента команды.
     *
     * @return строковое представление аргумента команды.
     */
    @Override
    public String stringArgument(){
        return "type";
    }

    
}
