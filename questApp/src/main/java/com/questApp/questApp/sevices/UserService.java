package com.questApp.questApp.sevices;

import com.questApp.questApp.entities.User;
import com.questApp.questApp.repos.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User saveOneUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateOneUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            User foundUser = user.get();
            foundUser.setUserName(newUser.getUserName());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        } else
            return null;
    }

    public Optional<User> deleteOneUser(Long userId) {
        // Java object to JSON string
        try {
            Optional<User> user = userRepository.findById(userId);
            userRepository.deleteById(userId);
            return user;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
