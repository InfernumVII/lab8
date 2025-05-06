package manager;

import java.io.EOFException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;

import managers.CommandManager;
import server.managers.exceptions.ParseCommandException;

public class TerminalWithCommandManager<T extends CommandManager<?>> {
    private Scanner scanner;
    protected T cManager;
    private final PrintStream nullPStream = new PrintStream(OutputStream.nullOutputStream());
    private final PrintStream defaultPStream = System.out;
    private final InputStream dInputStream = System.in;
    

    public TerminalWithCommandManager(T cManager){
        scanner = new Scanner(dInputStream);
        this.cManager = cManager;
    }

    public T getCommandManager() {
        return cManager;
    }

    
    public void setScanner(Scanner s){
        this.scanner = s;
    }

    public Scanner getScanner(){
        return scanner;
    }

    public void setOutputToNull(){
        System.setOut(nullPStream);
    }

    public void setOutputToDefault(){
        System.setOut(defaultPStream);
    }

    public void start(Consumer<String> afterFunction){
        while (true) {
            System.out.print("> ");
            String in;
            try {
                in = scanner.nextLine().trim();
            } catch (NoSuchElementException | IllegalStateException e) {
                break;
            }
            afterFunction.accept(in);
        }
        
    }

    public void start(){
        start(in -> {
            try {
                cManager.executeCommandFromRawInput(in);
            } catch (ParseCommandException e) {
                System.err.println(e.getMessage());
            }
        });
    }



}
