package server.commands;

import server.managers.ServerCommandManager;
import shared.network.models.Pair;
import shared.network.models.RegistrationEnums;
import shared.network.models.User;

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
