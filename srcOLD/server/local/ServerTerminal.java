package server.local;

import manager.TerminalWithCommandManager;
import managers.CommandManager;
import server.local.commands.Command;
import server.managers.DragonManager;

public class ServerTerminal extends TerminalWithCommandManager<ServerCommandManager> {
    public ServerTerminal(DragonManager dragonManager) {
        super(new ServerCommandManager(dragonManager));
    }    
}
