package server.psql.auth;

import java.security.SecureRandom;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import network.utility.SHA1;
import server.psql.PSQL;
//import server.psql.exceptions.UserNotFound;

public class Auth extends PSQL {
    private static final SecureRandom secureRandom = new SecureRandom();
    private static final String pepper = "darkdust";

    public Auth(Connection connection) {
        super(connection);
    }

    public boolean checkLoginIsExist(String login) throws SQLException{
        PreparedStatement pStatement = createPreparedStatement("SELECT * FROM auth WHERE login = ?");
        pStatement.setString(1, login); //TODO почичать реализацию
        ResultSet resultSet = pStatement.executeQuery();
        return resultSet.next();
    }

    public RegistrationEnums registerUser(server.psql.auth.User user){
        try {
            if (checkLoginIsExist(user.getLogin())){
                return RegistrationEnums.LOGIN_IS_EXIST;
            } else {
                boolean inserted = insertUser(user);
                if (inserted == true){
                    return RegistrationEnums.SUCCESSFUL;
                } else {
                    return RegistrationEnums.UNSUCCESSFUL;
                }
            }
        } catch (SQLException e){
            System.err.println(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }

    public int findUserId(User user){
        try {
            PreparedStatement pStatement = createPreparedStatement("SELECT id FROM auth WHERE login = ?");
            pStatement.setString(1, user.getLogin());
            pStatement.executeQuery();
            ResultSet resultSet = pStatement.getResultSet();
            if (resultSet.next()){
                return resultSet.getInt(1);
            } else {
                throw new RuntimeException("Ошибка");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }

    public boolean checkUserCreds(User user){
        try {
            if (checkLoginIsExist(user.getLogin()) == false){
                return false;
            } else {
                byte[] salt = getSaltByLogin(user.getLogin());
                String hashedPassword = makeHashedPassword(user.getPassword(), salt);
                PreparedStatement pStatement = createPreparedStatement("SELECT * FROM auth WHERE login = ? AND password = ?");
                pStatement.setString(1, user.getLogin());
                pStatement.setString(2, hashedPassword);
                pStatement.executeQuery();
                return pStatement.getResultSet().next();
            }
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private byte[] getSaltByLogin(String login) throws SQLException{
        PreparedStatement pStatement = createPreparedStatement("SELECT salt FROM auth WHERE login = ?");
        pStatement.setString(1, login);
        ResultSet resultSet = pStatement.executeQuery();
        if (resultSet.next()){
            return resultSet.getBytes(1);
        }
        return null;
    }

    public boolean insertUser(server.psql.auth.User user){
        try {
            PreparedStatement pStatement = createPreparedStatement("INSERT INTO auth (login, password, salt) values (?, ?, ?)");
            byte[] salt = makeSalt();
            pStatement.setString(1, user.getLogin());
            pStatement.setString(2, makeHashedPassword(user.getPassword(), salt));
            pStatement.setBytes(3, salt);
            int rowsInserted = pStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    private byte[] makeSalt(){
        byte[] salt = new byte[16];
        secureRandom.nextBytes(salt);
        return salt;
    }

    private String makeHashedPassword(String password, byte[] salt){
        return SHA1.getSHA1String(password, salt, pepper);
    }
    // public User getUser(String login) throws UserNotFound{
    //     try {
    //         PreparedStatement pStatement = createPreparedStatement("SELECT id, password FROM auth WHERE login = ?");
    //         pStatement.setString(1, login);
    //         ResultSet resultSet = pStatement.executeQuery();
    //         if (resultSet.next()){
    //             return new User(resultSet.getInt("id"), login, resultSet.getString("password"));
    //         } else {
    //             throw new UserNotFound();
    //         }
    //     } catch (SQLException e){
    //         System.err.println(e.getMessage());
    //         throw new RuntimeException(e.getMessage());
    //     }
    // }

    // public User getUserByAuthKey(String password) throws UserNotFound{
    //     try {
    //         PreparedStatement pStatement = createPreparedStatement("SELECT id, login FROM auth WHERE password = ?");
    //         pStatement.setString(1, password);
    //         ResultSet resultSet = pStatement.executeQuery();
    //         if (resultSet.next()){
    //             return new User(resultSet.getInt("id"), resultSet.getString("login"), password);
    //         } else {
    //             throw new UserNotFound();
    //         }
    //     } catch (SQLException e){
    //         System.err.println(e.getMessage()); //serr
    //         throw new RuntimeException(e.getMessage());
    //     }
    // }

    // public boolean userIsExists(String login){
    //     System.out.println(3);
    //     try {
    //         getUser(login);
    //         System.out.println(4);
    //     } catch (UserNotFound e) {
    //         return false;
    //     }
    //     return true;
    // }
    
    // public boolean passwordIsExist(String password){
    //     try {
	// 		PreparedStatement pStatement = createPreparedStatement("SELECT 1 FROM auth WHERE password = ?");
    //         pStatement.setString(1, password);
    //         ResultSet resultSet = pStatement.executeQuery();
    //         return resultSet.next();
	// 	} catch (SQLException e) {
	// 		System.err.println(e.getMessage());
    //         return false;
	// 	}
    // }

    // public boolean passwordCheck(String login, String password){
    //     try {
	// 		User user = getUser(login);
    //         return user.getPassword().equals(password);
            
	// 	} catch (UserNotFound e) {
	// 		return false;
	// 	}
    // }

    // public boolean insertUser(String login, String password){
    //     try {
    //         PreparedStatement pStatement = createPreparedStatement("INSERT INTO auth (login, password) values (?, ?)");
    //         pStatement.setString(1, login);
	// 		pStatement.setString(2, password);
    //         int rowsInserted = pStatement.executeUpdate();
    //         return rowsInserted > 0;
	// 	} catch (SQLException e) {
	// 		System.err.println(e.getMessage());
    //         return false;
	// 	}
    // }
}
