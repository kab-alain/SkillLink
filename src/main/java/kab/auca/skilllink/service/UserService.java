package kab.auca.skilllink.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.UserRepository;
import kab.auca.skilllink.response.MessageResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public MessageResponse registerUser(User user) {
        // Check if user already exists
        if (userRepository.existsByEmail(user.getEmail())) {
            return new MessageResponse("Error: Email is already taken!");
        }
        if (userRepository.existsByUsername(user.getName())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        // Encrypt password before saving
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    // Find user by email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by username
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Update user details
    public MessageResponse updateUser(Long userId, User updatedUser) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isPresent()) {
            updatedUser.setUserId(userId); // Keep the same userId
            userRepository.save(updatedUser);
            return new MessageResponse("User updated successfully!");
        } else {
            return new MessageResponse("Error: User not found!");
        }
    }

    // Delete user by ID
    public MessageResponse deleteUser(Long userId) {
        if (userRepository.existsById(userId)) {
            userRepository.deleteById(userId);
            return new MessageResponse("User deleted successfully!");
        } else {
            return new MessageResponse("Error: User not found!");
        }
    }
}
