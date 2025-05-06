package server;

import server.commands.ShowCommand;
import server.local.ServerCommandManager;

public class ServerShutDownThread extends Thread {
    private ServerCommandManager serverCommandManager;
    public ServerShutDownThread(ServerCommandManager serverCommandManager) {
        setName("ShutDownThread");
        this.serverCommandManager = serverCommandManager;
    }
    public void run(){
        serverCommandManager.executeCommand("save", null);
    };
}
