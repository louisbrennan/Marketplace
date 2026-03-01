package com.example.marketplace.service;

import com.example.marketplace.exception.EmailAlreadyExistsException;
import com.example.marketplace.exception.IncorrectPasswordException;
import com.example.marketplace.exception.UserNotFoundException;
import com.example.marketplace.exception.UsernameAlreadyExistsException;
import com.example.marketplace.repository.UserRepository;
import com.example.marketplace.model.User;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void registerUser(User user) {
        try {
            userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            String message = Objects.requireNonNull(e.getRootCause()).getMessage();

            if (message.contains("uq_username")) {
                throw new UsernameAlreadyExistsException();
            }
            if (message.contains("uq_email")) {
                throw new EmailAlreadyExistsException();
            }

            throw e;
        }
    }

    public User authenticateUser(String username, String password) {
        User user = userRepository
                .findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UserNotFoundException(username));

        if (!user.getPassword().equals(password)) {
            throw new IncorrectPasswordException();
        }

        return user;
    }
}
