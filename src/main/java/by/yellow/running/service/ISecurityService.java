package by.yellow.running.service;

public interface ISecurityService {
    String findLoggedInUsername();
    void autoLogin(String username, String password);
}
