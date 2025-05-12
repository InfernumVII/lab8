package client.commands;




import client.commands.utility.ConsoleInputHandler;
import client.commands.utility.DefaultDragon;


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
