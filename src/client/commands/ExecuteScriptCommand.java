package client.commands;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.temporal.Temporal;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

import client.ClientTerminal;
import client.ClientUdpNetwork;

public class ExecuteScriptCommand implements Command {
    private ClientTerminal terminal;
    private static Set<String> executedScripts = new HashSet<>();

    public ExecuteScriptCommand(ClientTerminal terminal){
        this.terminal = terminal;
    }


    @Override
    public boolean isHasArgs(){
        return true;
    }


    @Override
    public Object execute(Object argument){
        String arg = (String) argument;
        if (executedScripts.contains(arg)) {
            System.err.println("Ошибка: рекурсия обнаружена. Скрипт " + arg + " уже выполняется.");
            return null;
        }
            
        executedScripts.add(arg);

        System.out.println(String.format("Запуск команд из файла: %s", arg));

        Scanner scriptScanner = null;
        try (FileInputStream file = new FileInputStream(arg)) {
            scriptScanner = new Scanner(file);
            Scanner scannerBefore = terminal.getScanner();
            terminal.setScanner(scriptScanner);
            terminal.getCommandManager().initDefaultCommands(terminal);
            terminal.setOutputToNull();
            terminal.startLoop();
            terminal.setOutputToDefault();
            terminal.setScanner(scannerBefore);
            terminal.getCommandManager().initDefaultCommands(terminal);
            
            //scriptScanner.forEachRemaining(terminal::handleIter);
            //
            System.out.println("Все команды выполнены");
        } catch (IOException e) {
            System.err.println("Произошла ошибка: " + e.getMessage());
        } finally {
            executedScripts.remove(arg);
            scriptScanner.close();
        }
        return null;
        
        
    }

    
}
