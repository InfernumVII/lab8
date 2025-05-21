package shared.network;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;


import shared.network.exceptions.TimeOutException;
import shared.network.models.Answer;
import shared.network.models.NetCommandAuth;
import shared.network.utility.BytesConversions;

public abstract class UdpNetwork {
    protected DatagramChannel datagramChannel;
    protected InetSocketAddress inetSocketAddress;
    protected Selector selector;
    protected InetSocketAddress lastSender;

    public synchronized InetSocketAddress getLastSender(){
        return lastSender;
    }

    protected synchronized InetSocketAddress getSocketAddress(Settings settings) throws UnknownHostException{
        InetAddress ip = InetAddress.getByName(settings.getIp());
        int port = settings.getPort();
        return new InetSocketAddress(ip, port);
    }

    protected synchronized DatagramChannel createDatagramChannel() throws IOException{
        DatagramChannel datagramChannel = DatagramChannel.open(); 
        datagramChannel.configureBlocking(false); //Сетевые каналы должны использоваться в неблокирующем режиме. (В рамках моего кода лучше использовать блокируемый режим)
        selector = Selector.open();
        datagramChannel.register(selector, SelectionKey.OP_READ);
        return datagramChannel;
    }


    protected synchronized DatagramChannel createDatagramChannel(InetSocketAddress address) throws IOException{
        DatagramChannel datagramChannel = createDatagramChannel();
        datagramChannel.bind(inetSocketAddress);
        return datagramChannel;
    }
    
    public synchronized void send(byte[] bytes) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        datagramChannel.send(buffer, inetSocketAddress);
    }
    public synchronized void send(byte[] bytes, InetSocketAddress inetSocketAddress) throws IOException{
        ByteBuffer buffer = ByteBuffer.wrap(bytes);
        datagramChannel.send(buffer, inetSocketAddress);
    }

    public synchronized void sendObject(Object object) throws IOException{
        byte[] serialized = BytesConversions.objectToBytes(object);
        //send(BytesConversions.intToBytes(serialized.length));
        send(serialized);
    }

    public synchronized void sendObject(Object object, InetSocketAddress inetSocketAddress) throws IOException{
        byte[] serialized = BytesConversions.objectToBytes(object);
        //send(BytesConversions.intToBytes(serialized.length), inetSocketAddress);
        send(serialized, inetSocketAddress);
        
    }

    public synchronized byte[] receive(int len) throws IOException {
        byte[] buf = new byte[len];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
        selector.select(); // Блокировка до получения данных
        lastSender = (InetSocketAddress) datagramChannel.receive(byteBuffer);
        return buf;
    }

    public synchronized byte[] receive(int len, long timeout) throws IOException, TimeOutException{
        byte[] buf = new byte[len];
        ByteBuffer byteBuffer = ByteBuffer.wrap(buf);
        selector.select(timeout); // Блокировка до получения данных
        lastSender = (InetSocketAddress) datagramChannel.receive(byteBuffer);
        if (lastSender == null) { 
            throw new TimeOutException();
        }
        return buf;
    }

    public synchronized byte[] handleLen() throws IOException {
        return receive(4);
    }
    public synchronized byte[] handleLen(long timeout) throws IOException, TimeOutException {
        return receive(4, timeout);
    }
    
    public synchronized Object handleObject() throws IOException, ClassNotFoundException {
        //byte[] lenBytes = handleLen();
        //int length = BytesConversions.bytesToInt(lenBytes); //https://ru.stackoverflow.com/questions/817289/Как-узнать-длину-пакета-по-datagramchannel (Другой - это сначала передать int или long, содержащий размер передаваемых данных, а потом передать столько данных.)
        //if (length < 0) throw new IOException();
        byte[] buf = receive(65507);
        return BytesConversions.bytesToObject(buf);
    }

    public synchronized Object handleObject(long timeout) throws IOException, TimeOutException, ClassNotFoundException {
        //byte[] lenBytes = handleLen(timeout);
        //int length = BytesConversions.bytesToInt(lenBytes); //https://ru.stackoverflow.com/questions/817289/Как-узнать-длину-пакета-по-datagramchannel (Другой - это сначала передать int или long, содержащий размер передаваемых данных, а потом передать столько данных.)
        //if (length < 0) throw new IOException();
        byte[] buf = receive(65507, timeout);
        return BytesConversions.bytesToObject(buf);
    }

    public synchronized NetCommandAuth handleCommand() throws IOException, ClassNotFoundException {
        Object obj = handleObject();
        return (NetCommandAuth) obj;
    }

    public synchronized Answer handleAnswer(long timeout) throws IOException, TimeOutException, ClassNotFoundException{
        Object obj = handleObject(timeout);
        return (Answer) obj;
    }
    

    
}
