package client;


import java.io.IOException;
import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;

public class ClientUdpNetwork extends UdpNetwork {

    public ClientUdpNetwork(Settings settings) throws IOException{
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel();
    }

    public Answer sendAndGetAnswer(Object command) throws IOException, TimeOutException, ClassNotFoundException {
        sendObject(command);
        return handleAnswer(1000);
    }

    
}





    


