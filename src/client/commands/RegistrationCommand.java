package client.commands;

import java.io.Console;
import java.math.BigInteger;

import network.utility.SHA1;
import server.psql.auth.User;

public class RegistrationCommand implements Command {
	private User user;
	private final static Console console = System.console();
	
	@Override
	public Object execute(Object arg) {
		String login;
		do {
			login = console.readLine("Введите логин: "); 
		} while (!validateLogin(login));
		char[] passwordChars;
		do {
			passwordChars = console.readPassword("Введите пароль: ");
		} while (!validatePassword(passwordChars));
		user = new User(login, new String(passwordChars));
        return user;
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

	public User getUser(){
		return user;
	}

	@Override
	public boolean isHasArgs() {
		return false;
	}
    
}
