package server.psql;

import java.net.ServerSocket;

import server.psql.auth.Auth;
import server.psql.exceptions.BlankCreds;

public class Main {
    public static void main(String[] args) {
        try {
            Settings settings = new Settings.Builder()
                .getUserAndPasswordFromHome()
                .withDbName("studs").build();

            Manager manager = new Manager(settings);
            Auth auth = new Auth(manager.getConnection());
        } catch (BlankCreds e) {
            e.printStackTrace();
        }
        
    }
}
