package server;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

import network.Settings;
import network.UdpNetwork;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import network.models.NetCommandAuth;
import server.managers.DragonManager;
import server.managers.ServerCommandManager;
import server.managers.exceptions.DragonFindException;
import server.psql.auth.Pair;
import server.psql.auth.User;

public class ServerUdpNetwork extends UdpNetwork {
    private DragonManager dManager;
    private ServerCommandManager serverCommandManager;

    ExecutorService responseSender;
    ForkJoinPool commandProcessor;
    
    public ServerUdpNetwork(Settings settings) throws IOException {
        inetSocketAddress = getSocketAddress(settings);
        datagramChannel = createDatagramChannel(inetSocketAddress);

        responseSender = Executors.newCachedThreadPool();
        commandProcessor = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        initCommandManager();
    }

    public void initCommandManager() {
        dManager = new DragonManager();
        serverCommandManager = new ServerCommandManager();
        serverCommandManager.initDefaultCommands(dManager);
    }

    public DragonManager getDragonManager() {
        return dManager;
    }

    public void start(boolean condition) throws ClassNotFoundException, IOException {
        System.out.println("Сервер запущен");

        while (condition) {
            final NetCommandAuth command = handleCommand();
            final InetSocketAddress lastSender = getLastSender();

            new Thread(() -> {
                commandProcessor.submit(() -> {
                    //System.out.println(command.user());
                    Answer answer = new Answer(serverCommandManager.executeCommand(command.command(),
                            new Pair<User, Object>(command.user(), command.arg())));
                    responseSender.submit(() -> {
                        try {
                            sendObject(answer, lastSender);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
                });
            }).start();
        }
    }
}
