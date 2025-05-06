package client;

import java.io.ByteArrayInputStream;
import java.io.Console;
import java.io.IOException;
import java.io.InputStream;
import java.io.SequenceInputStream;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import client.commands.RegistrationCommand;
import network.Settings;
import network.exceptions.TimeOutException;
import network.models.Answer;
import network.models.NetCommand;
import network.models.NetCommandAuth;
import server.managers.exceptions.ParseCommandException;
import server.psql.auth.RegistrationEnums;
import server.psql.auth.User;

public class ClientTerminalWithAuth extends ClientTerminal {
    private RegistrationCommand registrationCommand = new RegistrationCommand();
    private static boolean started = false;
    private static User checkUser;
    private final static Console console = System.console();
    public ClientTerminalWithAuth(ClientUdpNetwork cUdpNetwork){
        super(cUdpNetwork);
        makeAuthOrReg();
        //addRegistration();
    }

    private void makeAuthOrReg(){
        System.out.println("Для продолжения пользования утилитой Вы должны пройти авторизацию\nВы хотите авторизоваться или зарегистрироваться? (1/2)");
        String input;
        int choose;
        do {
            input = getScanner().nextLine();
        } while ((choose = validateChoose(input)) == -1);
        
        User user = requestCreds();
        switch (choose) {
            case 2:
                doReg(user);
                break; 
            case 1:
                doAuth(user);
                break;
        }
    }

    private void doReg(User user){
        NetCommandAuth netCommandAuth = new NetCommandAuth("reg", user, user);
        try {
            Answer answer = cUdpNetwork.sendAndGetAnswer(netCommandAuth);
            RegistrationEnums answerE = (RegistrationEnums) answer.answer();
            switch (answerE) {
                case LOGIN_IS_EXIST:
                    System.out.println("Пользователь с таким логином уже существует");
                    System.exit(0);
                    break;
                case SUCCESSFUL:
                    System.out.println("Успешная регистрация");
                    checkUser = user;
                    break;
                case UNSUCCESSFUL:
                    System.out.println("Ошибка регистрации");
                    System.exit(0);
            }
        } catch (IOException | ClassNotFoundException | TimeOutException e){
            throw new RuntimeException(e);
        }
    }

    private void doAuth(User user){
        NetCommandAuth netCommandAuth = new NetCommandAuth("auth", user, user);
        try {
            Answer answer = cUdpNetwork.sendAndGetAnswer(netCommandAuth);
            boolean answerB = (boolean) answer.answer();
            if (answerB == true){
                System.out.println("Успешная авторизация");
                checkUser = user;
            } else {
                System.out.println("Неправильный логин или пароль");
                System.exit(0);
            }
        } catch (IOException | ClassNotFoundException | TimeOutException e){
            throw new RuntimeException(e);
        }
    }

    private boolean validateLogin(String username){
		
		if (username.length() < 4){
			System.err.println("Минимальная длина логина 4 символа");
			return false;
		} else {
			if (username.length() > 20){
				System.err.println("Максимальная длина логина 20 символов");
				return false;
			}
		}
		return true;
	}

	private boolean validatePassword(char[] passwordChars){
		String password = new String(passwordChars);
		if (password.length() < 4){
			System.err.println("Минимальная длина пароля 4 символа");
			return false;
		} else {
			if (password.length() > 100){
				System.err.println("Максимальная длина пароля 100 символов");
				return false;
			}
		}
		return true;
	}
    private User requestCreds(){
        String login;
		do {
			login = console.readLine("Введите логин: "); 
		} while (!validateLogin(login));
		char[] passwordChars;
		do {
			passwordChars = console.readPassword("Введите пароль: ");
		} while (!validatePassword(passwordChars));
		return new User(login, new String(passwordChars));
    }
    private int validateChoose(String input){
        return switch (input) {
            case "1" -> 1;
            case "2" -> 2;
            default -> {
                System.out.println("Вы хотите авторизоваться или зарегистрироваться? (1/2)");
                yield -1;
            }
        };
    }
    // private void addRegistration(){
    //     //cManager.registerCommand("auth", registrationCommand);
    //     //setScanner(new Scanner(makeSeqStream()));
    // }

    // private InputStream makeSeqStream(){
    //     InputStream firstStream = new ByteArrayInputStream("auth\n".getBytes());
    //     InputStream combinedStream = new SequenceInputStream(firstStream, System.in);
    //     return combinedStream;
    // }

    @Override
    public void handleIter(String in){
        try {
            NetCommand netCommand = cManager.getCommandFromRawInput(in);
            NetCommandAuth netCommandAuth = new NetCommandAuth(netCommand.command(), netCommand.arg(), checkUser);
            Answer answer = cUdpNetwork.sendAndGetAnswer(netCommandAuth);
            smartPrint(answer.answer());
        } catch (TimeOutException | ParseCommandException e) {
            System.err.println(e.getMessage());
        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }
        
    }

    
}
