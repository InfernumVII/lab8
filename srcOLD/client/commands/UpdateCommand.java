package client.commands;

import java.util.Scanner;

import client.commands.utility.ArgHandler;
import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.DefaultDragon;
import collection.Dragon;
import collection.Dragon.Builder;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;
import network.models.UpdateCommandArgs;


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
