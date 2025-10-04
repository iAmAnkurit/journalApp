package com.ankurit.journalApp.service;

import com.ankurit.journalApp.entity.JournalEntry;
import com.ankurit.journalApp.entity.User;
import com.ankurit.journalApp.repository.UserRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUserEntry(User user){
        userRepository.save(user);
    }

    public List<User> getAllUser()
    {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id)
    {
        return userRepository.findById(id);
    }

    public void  deleteUser(ObjectId id)
    {
        userRepository.deleteById(id);
    }

    public User findByUserName(String userName)
    {
        return userRepository.findByUserName(userName);
    }
}
