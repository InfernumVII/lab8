package client.commands;

import client.commands.utility.ConsoleInputHandler;
import shared.network.models.RemoveGreaterCommandArgs;



public class RemoveGreaterCommand implements Command {

    private ConsoleInputHandler consoleInputHandler;
    public RemoveGreaterCommand(ConsoleInputHandler consoleInputHandler){
        this.consoleInputHandler = consoleInputHandler;
    }


    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(Object arg){
        System.out.println("Введите координаты элемента: ");
        long x = consoleInputHandler.promptForLong("Введите координату x:", false, -1000, 1000);
        long y = consoleInputHandler.promptForLong("Введите координату y:", false, -1000, 1000);
        return new RemoveGreaterCommandArgs(x, y);
    }

}
