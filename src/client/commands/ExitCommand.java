package client.commands;


public class ExitCommand implements Command {


    @Override
    public boolean isHasArgs(){
        return false;
    }

    @Override
    public Object execute(Object arg){
        System.exit(0);
        return null;
    }
}
