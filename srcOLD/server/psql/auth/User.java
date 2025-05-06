package server.psql.auth;

import java.io.Serializable;

public class User implements Serializable{
    private static final long serialVersionUID = 1L;
    private final String login;
    private final String password;

    public User(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("User{login='%s', password='%s'}",
                login, password);
    }

    
}