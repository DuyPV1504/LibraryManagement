package org.example.demo.service;

import models.User;
import org.example.demo.data.UserRepository;

import java.util.List;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        userRepository = new UserRepository();
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    public User getUserByAccount(String userAccount) {
        return userRepository.getUserByAccount(userAccount);
    }

    public boolean addUser(User user) {
        return userRepository.addUser(user);
    }

    public boolean updateUser(User user) {
        return userRepository.updateUser(user);
    }

    public boolean deleteUser(int id) {
        return userRepository.deleteUser(id);
    }

    public List<User> searchUserByKeyword(String keyword) {
        return userRepository.searchUserByKeyword(keyword);
    }
}


