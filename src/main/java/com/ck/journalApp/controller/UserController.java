package com.ck.journalApp.controller;

import com.ck.journalApp.entity.JournalEntry;
import com.ck.journalApp.entity.User;
import com.ck.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAll();
    }

    @PostMapping
    public void createUser(@RequestBody User user){
        userService.saveUser(user);
    }

    @PutMapping("/{userName}")
    public ResponseEntity<?>  updateUser(@RequestBody User user, @PathVariable String userName){
        User userInDb= userService.findByUserName(userName);
        if(userInDb!=null){
            userInDb.setUserName(user.getUserName());
            userInDb.setPassword(user.getPassword());
            userService.saveUser(userInDb);
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
