package server;

import java.io.IOException;

import network.Settings;
import network.exceptions.TimeOutException;
import server.local.ServerTerminal;
import server.psql.Manager;
import server.psql.exceptions.BlankCreds;

public class ServerMain {
    private static Manager managerInstance = initPSQLManager();
    private static Manager initPSQLManager(){
        server.psql.Settings settings;
        try {
            settings = new server.psql.Settings.Builder()
                    .getUserAndPasswordFromHome()
                    .withDbName("studs").build(); 
        } catch (BlankCreds e) {
            throw new RuntimeException(e);
        }
        return new Manager(settings);
    }
    public static Manager getManagerInsance(){
        return managerInstance;
    }
    public static void main(String[] args) {
        try {
            Settings settings = new ServerSettings();
            ServerUdpNetwork server = new ServerUdpNetwork(settings);
            ServerTerminal sTerminal = new ServerTerminal(server.getDragonManager());
            //Runtime.getRuntime().addShutdownHook(new ServerShutDownThread(sTerminal.getCommandManager())); // https://stackoverflow.com/questions/5124439/java-console-program-and-ctrl-c
            ServerTerminalThread sTerminalThread = new ServerTerminalThread(sTerminal);
            
            sTerminalThread.start();
            server.start(true);
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        } 
    }
}