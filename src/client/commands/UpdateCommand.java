package client.commands;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import client.commands.utility.DefaultDragon;
import shared.collection.Dragon;


public class UpdateCommand implements Command {
    private ConsoleInputHandler consoleInputHandler;

    public UpdateCommand(ConsoleInputHandler consoleInputHandler){
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public boolean isHasArgs(){
        return true;
    }

    @Override
    public Object execute(Object argument){
        String arg = (String) argument;
        try {
            if (ArgHandler.checkArgForInt(arg)){
                int id = Integer.parseInt(arg);                    
                System.out.println(String.format("Начинаем изменение дракона с ID-%d", id));
    
                Dragon dragon = new DefaultDragon(consoleInputHandler).get().withId(id).build();
                

                //System.out.println(String.format("Дракон с ID-%d успешно обновлён!", id));

                return dragon;
                //return new UpdateCommandArgs(id, name, x, y, age, color, type, character, eyesCount);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
        
    }
    
}
