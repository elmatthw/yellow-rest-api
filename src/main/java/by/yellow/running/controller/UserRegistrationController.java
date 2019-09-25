package by.yellow.running.controller;

import by.yellow.running.entity.User;
import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UsernameExistsException;
import by.yellow.running.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    public UserService service;

    @PostMapping
    public @ResponseBody User registerAccount(@RequestBody User user) {
        User registered = createUserAccount(user);
        return registered;
    }

    private User createUserAccount(User user){
        User registered;
        try {
            registered = service.registerNewAccount(user);
        } catch (UsernameExistsException e) {
            return null;
        } catch (PasswordDoesntMatch e) {
            return null;
        }
        return registered;
    }
}
