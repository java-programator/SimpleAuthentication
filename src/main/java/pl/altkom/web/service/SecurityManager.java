package pl.altkom.web.service;

import java.util.List;

public interface SecurityManager {
    boolean isCorrectPassword(String login, String password);
    List<String> getAllUsernames();
    boolean addUser(String login, String password);
}
