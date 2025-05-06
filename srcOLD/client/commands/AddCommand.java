package client.commands;

import java.time.LocalDate;
import java.util.Scanner;


import client.commands.utility.ConsoleInputHandler;
import collection.Color;
import collection.Coordinates;
import collection.DefaultDragon;
import collection.Dragon;
import collection.Dragon.Builder;
import collection.DragonCharacter;
import collection.DragonHead;
import collection.DragonType;

public class AddCommand implements Command {

    private ConsoleInputHandler consoleInputHandler;
    public AddCommand(ConsoleInputHandler consoleInputHandler){
        this.consoleInputHandler = consoleInputHandler;
    }

    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(Object arg) {
        return new DefaultDragon(consoleInputHandler).get();
    }

   
}
