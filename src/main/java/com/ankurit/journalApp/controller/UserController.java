package com.ankurit.journalApp.controller;


import com.ankurit.journalApp.entity.User;
import com.ankurit.journalApp.repository.UserRepository;
import com.ankurit.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAllUser()
    {
       return userService.getAllUser();
    }

    @PostMapping
    public void createUser(@RequestBody User user)
    {
        userService.saveUserEntry(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?> updateUser(@RequestBody User user, @PathVariable String userName) {
        User userInDb = userService.findByUserName(userName);

        if (userInDb != null) {
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveUserEntry(userInDb);

            return ResponseEntity.ok(userInDb);
        }

        return ResponseEntity.notFound().build();
    }

}
