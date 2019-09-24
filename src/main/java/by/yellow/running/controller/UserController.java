package by.yellow.running.controller;

import by.yellow.running.entity.User;
import by.yellow.running.repository.RunningRepository;
import by.yellow.running.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {
    /*@Autowired
    private UserService userService;*/
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RunningRepository runningRepository;
    /*@Autowired
    private UserValidator userValidator;*/
    /*@Autowired
    private ISecurityService securityService;*/

    /*@Autowired
    public UserController(UserService userService, UserRepository userRepository, UserValidator userValidator) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.userValidator = userValidator;
    }*/

    /*@Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }*/



    /*@PostMapping("/signup")
    public @ResponseBody User signUp(@RequestParam String username,
                                     @RequestParam String password,
                                     @RequestParam String email,
                                     @RequestParam String confirmPassword,
                                     BindingResult bindingResult){
        User newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(password);
        newUser.setPasswordConfirm(confirmPassword);
        userValidator.validate(newUser, bindingResult);

        if(bindingResult.hasErrors()){
            return null;
        }

        userService.save(newUser);

        securityService.autoLogin(newUser.getUsername(), newUser.getPassword());

        return newUser;
    }*/

    /*@PostMapping("/login")
    public @ResponseBody User logIn()*/

    @GetMapping("/")
    public @ResponseBody
    Iterable<User> getUsers(){
        return userRepository.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    Optional<User> getUser(@PathVariable String id){
        return userRepository.findById(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteUser(@PathVariable String id){
        userRepository.deleteById(Long.parseLong(id));
        return "User deleted";
    }

    @DeleteMapping("/")
    public @ResponseBody String deleteAllUsers(){
        userRepository.deleteAll();
        return "User deleted";
    }
    @PostMapping("/")
    public @ResponseBody User addNewUser(@RequestBody User user){
        return userRepository.save(user);
    }
}
