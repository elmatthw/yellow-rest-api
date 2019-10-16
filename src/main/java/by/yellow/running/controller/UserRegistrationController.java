package by.yellow.running.controller;

import by.yellow.running.exception.PasswordDoesntMatch;
import by.yellow.running.exception.UsernameExistsException;
import by.yellow.running.model.User;
import by.yellow.running.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/registration")
public class UserRegistrationController {
    @Autowired
    public UserService service;

    @PostMapping
    public @ResponseBody
    User registerAccount(@RequestBody User user) {
        User registered = createUserAccount(user);
        return registered;
    }

    private User createUserAccount(User user){
        User registered;
        try {
            registered = service.registerNewAccount(user);
        } catch (UsernameExistsException | PasswordDoesntMatch e) {
            return null;
        }
        return registered;
    }
}
