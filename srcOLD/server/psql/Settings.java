package server.psql;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import server.psql.exceptions.BlankCreds;

public class Settings {
    private String host = "localhost";
    private int port = 5432;
    private String username;
    private String password;
    private String dbName;

    private Settings(Builder builder){
        this.host = builder.host;
        this.port = builder.port;
        this.username = builder.username;
        this.password = builder.password;
        this.dbName = builder.dbName;
    }
    
    public static class Builder {
        private String host = "localhost";
        private int port = 5432;
        private String username;
        private String password;
        private String dbName;

        public Builder withHost(String host){
            this.host = host;
            return this;
        }

        public Builder withPort(int port){
            this.port = port;
            return this;
        }

        public Builder withUserName(String username){
            this.username = username;
            return this;
        }

        public Builder withPassWord(String password){
            this.password = password;
            return this;
        }

        public Builder withDbName(String dbName){
            this.dbName = dbName;
            return this;
        }

        public Builder getUserAndPasswordFromHome(){
            Path p = Paths.get(System.getProperty("user.home"), ".pgpass");
            String s = null;
            try {
                s = Files.readString(p);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
            String[] splitted = s.split(":");
            String user = splitted[3].trim();
            String pass = splitted[4].trim();
            this.username = user;
            this.password = pass;
            return this;
        }

        public Settings build() throws BlankCreds{
            if (this.username.isEmpty() | this.password.isEmpty()){
                throw new BlankCreds();
            }
            return new Settings(this);
        }

    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getDbName() {
        return dbName;
    }
    

}
