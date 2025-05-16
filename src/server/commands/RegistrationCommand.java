package server.commands;

import server.managers.ServerCommandManager;
import server.psql.auth.RegistrationEnums;
import server.psql.auth.User;
import shared.network.models.Pair;

public class RegistrationCommand implements Command {

	@Override
	public Object execute(Object argument, User user) {
        User userToRegister = (User) argument;
		int userId = -1;
		RegistrationEnums registerUser = ServerCommandManager.getAuthInstance().registerUser(userToRegister);
		if (registerUser == RegistrationEnums.SUCCESSFUL){
			userId = ServerCommandManager.getAuthInstance().findUserId(userToRegister);
		}
		return new Pair<RegistrationEnums, Integer>(registerUser, userId);
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
