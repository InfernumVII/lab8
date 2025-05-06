package server.psql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Manager {
    private Connection connection;
    
    public Manager(Settings settings){
        Properties props = getProperties(settings.getUsername(), settings.getPassword());
        String url = getUrlToConnect(settings);
        try {
            connection = DriverManager.getConnection(url, props);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
        System.out.println("PSQL Connected");
    }

    private String getUrlToConnect(Settings settings){
        return String.format("jdbc:postgresql://%s:%d/%s", settings.getHost(), settings.getPort(), settings.getDbName());
    } 

    private Properties getProperties(String user, String pass){
        Properties properties = new Properties();
        properties.setProperty("user",user);
        properties.setProperty("password",pass);
        return properties;
    }

    public Connection getConnection() {
        return connection;
    }
    
}
