package com.management.service;

import com.management.entity.User;
import com.management.exception.NotFoundException;
import com.management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(NotFoundException::new);
    }

    public User createUser(User user) {
        return userRepository.save(user);
    }

    public void removeUserById(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
