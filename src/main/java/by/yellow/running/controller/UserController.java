package by.yellow.running.controller;

import by.yellow.running.model.User;
import by.yellow.running.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public @ResponseBody
    Collection<User> getUsers(){
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public @ResponseBody
    User getUser(@PathVariable String id){
        return userService.findById(Long.parseLong(id));
    }

    @DeleteMapping("/{id}")
    public @ResponseBody String deleteUser(@PathVariable String id){
        userService.deleteById(Long.parseLong(id));
        return "User deleted";
    }

    @PostMapping("/")
    public @ResponseBody
    User addNewUser(@RequestBody User user){
        return userService.save(user);
    }
}
