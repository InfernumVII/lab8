package client;

import java.io.IOException;

import client.managers.ClientCommandManager;
import javafx.stage.Stage;
import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import server.managers.exceptions.ParseCommandException;

public class ClientMain {
    public static void main(String[] args) {
        Settings settings = new ClientSettings();
        ClientUdpNetwork client;
        try {
            client = new ClientUdpNetwork(settings);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ClientTerminal clientTerminal = new ClientTerminalWithAuth(client);
        clientTerminal.startLoop();
        
    }

    public static Stage getPrimaryStage() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPrimaryStage'");
    }
}
