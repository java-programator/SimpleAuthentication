package pl.altkom.web.service;

import pl.altkom.web.dao.UserDao;
import pl.altkom.web.dao.UserDaoImpl;
import pl.altkom.web.exception.AuthenticationException;
import pl.altkom.web.exception.UnknownUserException;
import pl.altkom.web.exception.UsernameAlreadyTakenException;
import pl.altkom.web.exception.WrongPasswordException;
import pl.altkom.web.model.User;

import java.util.ArrayList;
import java.util.List;

public class SecurityManagerImpl implements SecurityManager {

    private UserDao userDao;

    public SecurityManagerImpl() throws AuthenticationException {
        userDao = new UserDaoImpl();
    }

    @Override
    public boolean isCorrectPassword(String login, String password) {
        try {
            userDao.checkUser(new User(login, password));
            return true;
        } catch (UnknownUserException | WrongPasswordException e) {
            e.printStackTrace();
            return false;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    @Override
    public List<String> getAllUsernames() {
        List<String> result = new ArrayList<>();
        List<User> users;
        try {
            users = userDao.getAllUsers();
        } catch (AuthenticationException e) {
            e.printStackTrace();
            return result;
        }
        for (User user : users) {
            result.add(user.getLogin());
        }
        return result;
    }

    @Override
    public boolean addUser(String login, String password) {
        try {
            userDao.addUser(new User(login, password));
            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }
}
