package server.local;

import shared.managers.TerminalWithCommandManager;
import server.managers.DragonManager;

public class ServerTerminal extends TerminalWithCommandManager<ServerCommandManager> {
    public ServerTerminal(DragonManager dragonManager) {
        super(new ServerCommandManager(dragonManager));
    }    
}
