package pl.altkom.web.dao;

import pl.altkom.web.exception.AuthenticationException;
import pl.altkom.web.exception.UnknownUserException;
import pl.altkom.web.exception.WrongPasswordException;
import pl.altkom.web.model.User;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private Connection connection;
    private PreparedStatement checkUserStatement;
    private PreparedStatement getUsers;
    private PreparedStatement addUser;

    public UserDaoImpl() throws AuthenticationException {
        try {
            InitialContext initCtx = new InitialContext();
            Context context = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) context.lookup("jdbc:users");
            connection = ds.getConnection();
            checkUserStatement = connection.prepareStatement("SELECT * FROM users where name = ?");
            getUsers = connection.prepareStatement("SELECT * FROM users");
            addUser = connection.prepareStatement("INSERT INTO users VALUES (?, ?)");
        } catch (NamingException | SQLException e) {
            throw new AuthenticationException("Error during configuring database connection", e);
        }
    }

    @Override
    public List<User> getAllUsers() throws AuthenticationException {
        ResultSet rs = null;
        try {
            rs = getUsers.executeQuery();
            List<User> users = new ArrayList<>();
            while (rs.next()) {
                String login = rs.getString(1);
                String password = rs.getString(2);
                users.add(new User(login, password));
            }
            return users;
        } catch (SQLException e) {
            throw new AuthenticationException("Error during obtaining list of users");
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new AuthenticationException("Error during obtaining list of users");
                }
            }
        }
    }

    @Override
    public void checkUser(User user) throws AuthenticationException {
        ResultSet rs = null;
        try {
            checkUserStatement.setString(1, user.getLogin());
            rs = checkUserStatement.executeQuery();
            if (rs.next()) {
                String actualPassword = rs.getString(2);
                if (!user.getPassword().equals(actualPassword)) {
                    throw new WrongPasswordException("Provided password does not match for user '" + user.getLogin() + "'");
                }
            } else {
                throw new UnknownUserException("Provided username '" + user.getLogin() + "' does not exists");
            }
        } catch (SQLException e) {
            throw new AuthenticationException("Cannot check password", e);
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    throw new AuthenticationException("Cannot close connection", e);
                }
            }
        }
    }

    @Override
    public void addUser(User user) throws AuthenticationException {
        try {
            addUser.setString(1, user.getLogin());
            addUser.setString(2, user.getPassword());
            addUser.executeUpdate();
        } catch (SQLException e) {
            throw new AuthenticationException("Cannot add user to database");
        }
    }
}
