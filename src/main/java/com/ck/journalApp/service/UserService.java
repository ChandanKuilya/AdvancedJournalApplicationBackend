package com.ck.journalApp.service;

import com.ck.journalApp.entity.User;
import com.ck.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void saveUser(User user){
        userRepository.save(user);
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public Optional<User> findById(ObjectId id){
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id){
        userRepository.deleteById(id);
    }
    public User findByUserName(String username){
        return userRepository.findByUserName(username);
    }

}
