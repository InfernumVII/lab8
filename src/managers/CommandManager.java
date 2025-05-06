package managers;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import server.managers.exceptions.ParseCommandException;

public abstract class CommandManager<V> {
    private final static int HISTORY_SIZE = 5;
    private Deque<String> history = new ArrayDeque<String>(HISTORY_SIZE);
    protected Map<String, V> commands = new HashMap<>();

    public void registerCommand(String name, V command){
        commands.put(name, command);
    }
    public abstract Object executeCommand(String name, Object arg);

    public Deque<String> getHistory(){
        return history;
    }

    public Map<String, V> getCommands() {
        return commands;
    }

    public Set<String> listedNames(){
        return commands.keySet();
    }

    protected void storeCommand(String command){
        if (history.size() >= HISTORY_SIZE) {
            history.removeFirst(); 
        }
        history.addLast(command);
    }

    public void executeCommandFromRawInput(String in) throws ParseCommandException{
        String[] parsedCommand = parseCommand(in);
        executeCommand(parsedCommand[0], parsedCommand[1]);
    }

    public String[] parseCommand(String command) throws ParseCommandException{
        String[] input = deleteBlankArguments(command.split(" "));
        String commandName = input[0];
        String commandArg = null;
        if (input.length > 2){
            throw new ParseCommandException();
        }
        if (input.length == 2){
            commandArg = input[1];
        }
        return new String[]{commandName, commandArg};
    }
    private String[] deleteBlankArguments(String[] splitted){
        return Stream.of(splitted).filter(st -> !st.trim().isBlank()).toArray(String[]::new);
    }
}
