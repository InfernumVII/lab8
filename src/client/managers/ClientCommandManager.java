package client.managers;

import java.time.temporal.Temporal;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.function.Predicate;

import client.ClientTerminal;
import client.ClientUdpNetwork;
import client.commands.AddCommand;
import client.commands.Command;
import client.commands.ExecuteScriptCommand;
import client.commands.ExitCommand;
import client.commands.RemoveGreaterCommand;
import client.commands.UpdateCommand;
import client.commands.utility.ConsoleInputHandler;
import managers.CommandManager;
import network.models.NetCommand;
import server.managers.exceptions.ParseCommandException;


public class ClientCommandManager extends CommandManager<Command> {

    public void initDefaultCommands(ClientTerminal terminal){
        commands.clear();
        ConsoleInputHandler consoleInputHandler = new ConsoleInputHandler(terminal.getScanner());
        registerCommand("add", new AddCommand(consoleInputHandler));
        registerCommand("update", new UpdateCommand(consoleInputHandler));
        registerCommand("add_if_min", new AddCommand(consoleInputHandler));
        registerCommand("remove_greater", new RemoveGreaterCommand(consoleInputHandler));
        registerCommand("execute_script", new ExecuteScriptCommand(terminal));
        registerCommand("exit", new ExitCommand());
    }

    private Object makeArgsIfCommandIsLocal(String command, Object commandArgs){
        if (listedNames().contains(command)){
            Object answer = executeCommand(command, (String) commandArgs);
            if (answer != null){
                commandArgs = answer;
            }
        }
        return commandArgs;
    }

    public NetCommand getCommandFromRawInput(String in) throws ParseCommandException{
        String[] parsedCommand = parseCommand(in);
        String command = parsedCommand[0];
        Object commandArgs = parsedCommand[1];
        commandArgs = makeArgsIfCommandIsLocal(command, commandArgs);
        return new NetCommand(command, commandArgs);
    }

    @Override
    public Object executeCommand(String name, Object arg){
        Command command = commands.get(name);
        if (command != null) {
            if (command.isHasArgs() == true && arg == null){
                return "У команды должны быть аргументы.";
            }
            else if (command.isHasArgs() == false && arg != null){
                return "У команды не может быть аргументов.";
            } else {
                return command.execute(arg);
            }
        }
        return null;
    }
}
