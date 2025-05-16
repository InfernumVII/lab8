package server.commands;

import server.managers.ServerCommandManager;
import shared.network.models.Pair;
import shared.network.models.User;

public class AuthCommand implements Command {
	@Override
	public Object execute(Object argument, User user) {
		User userToAuth = (User) argument;
		boolean checkUserCreds = ServerCommandManager.getAuthInstance().checkUserCreds(userToAuth);
		int userId = -1;
		if (checkUserCreds){
			userId = ServerCommandManager.getAuthInstance().findUserId(userToAuth);
		}
		return new Pair<Boolean,Integer>(checkUserCreds, userId);
		// String username = (String) argument;
		// Auth auth = ServerCommandManager.getAuthInstance();
		// if (auth.userIsExists(username)) {
		// 	if (auth.passwordCheck(username, authKey)) {
		// 		return "Авторизация успешна";
		// 	} else {
		// 		return "Неправильный логин или пароль";
		// 	}
		// } else {
		// 	if (auth.insertUser(username, authKey)) {
		// 		return "Пользователь зарегистрирован";
		// 	} else {
		// 		return "Ошибка авторизации";
		// 	}
		// }
		
	}

	@Override
	public String getDescription() {
		return "Авторизация пользователя";
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
