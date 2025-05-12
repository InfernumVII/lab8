package server.commands;

import server.managers.ServerCommandManager;
import server.psql.auth.User;

public class RegistrationCommand implements Command {

	@Override
	public Object execute(Object argument, User user) {
        User userToRegister = (User) argument;
		return ServerCommandManager.getAuthInstance().registerUser(userToRegister);
	}

	@Override
	public String getDescription() {
		return "Регистрация пользователя";
	}

	@Override
	public boolean isHasArgs() {
		return true;
	}

    @Override
    public boolean isHiddenCommand(){
        return true;
    }
    
}
