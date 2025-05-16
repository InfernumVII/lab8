package client;


import java.io.IOException;
import shared.network.Settings;
import shared.network.UdpNetwork;
import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommand;

public class ClientUdpNetwork extends UdpNetwork {

    public ClientUdpNetwork(Settings settings) throws IOException{
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel();
    }

    public Answer sendAndGetAnswer(Object command) throws IOException, TimeOutException, ClassNotFoundException {
        sendObject(command);
        return handleAnswer(10000);
    }

    
}





    


