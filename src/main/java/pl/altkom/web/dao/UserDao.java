package pl.altkom.web.dao;

import pl.altkom.web.exception.AuthenticationException;
import pl.altkom.web.model.User;

import java.util.List;

public interface UserDao {
    List<User> getAllUsers() throws AuthenticationException;
    void checkUser(User user) throws AuthenticationException;
    void addUser(User user) throws AuthenticationException;

}
