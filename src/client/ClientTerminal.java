package client;

import java.io.IOException;
import java.util.Collection;

import client.commands.Command;
import client.managers.ClientCommandManager;
import manager.TerminalWithCommandManager;
import managers.CommandManager;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import server.managers.exceptions.ParseCommandException;

public class ClientTerminal extends TerminalWithCommandManager<ClientCommandManager> {
    protected ClientUdpNetwork cUdpNetwork;
    public ClientTerminal(ClientUdpNetwork cUdpNetwork) {
        super(new ClientCommandManager());
        this.cUdpNetwork = cUdpNetwork;
        cManager.initDefaultCommands(this);
    }
    
    public void startLoop(){
        start(this::handleIter);
    }
    
    public void handleIter(String in){
        try {
            NetCommand netCommand = cManager.getCommandFromRawInput(in);
            Answer answer = cUdpNetwork.sendAndGetAnswer(netCommand);
            smartPrint(answer.answer());
        } catch (TimeOutException | ParseCommandException e) {
            System.err.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        
    }

    public void smartPrint(Object in){
        if (in instanceof Collection){
            ((Collection<?>) in).stream().forEachOrdered(System.out::println);
        } else {
            System.out.println(in);
        }
    }
    
}
