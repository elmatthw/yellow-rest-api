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
    @Autowired
    private UserRepository userRepository;

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
