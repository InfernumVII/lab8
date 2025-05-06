package server;


import server.local.ServerTerminal;

public class ServerTerminalThread extends Thread {
    private ServerTerminal serverTerminal;
    public ServerTerminalThread(ServerTerminal serverTerminal){
        setDaemon(false);
        setName("ServerTerminalThread");
        this.serverTerminal = serverTerminal;
    }
    public void run(){
        serverTerminal.start();
    }
}
