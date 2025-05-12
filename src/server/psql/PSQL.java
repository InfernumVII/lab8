package server.psql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class PSQL {
    private final Connection connection;

    public PSQL(Connection connection){
        this.connection = connection;
    }
    protected Statement getStatement(){
        try {
            Statement statement = getConnection().createStatement();
            return statement;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
    protected PreparedStatement createPreparedStatement(String query){
        try {
            return getConnection().prepareStatement(query);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    protected Connection getConnection() {
        return connection;
    }
    
}