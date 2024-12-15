package kab.auca.skilllink.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.UserRepository;
import kab.auca.skilllink.response.MessageResponse;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register a new user
    public MessageResponse registerUser(User user, MultipartFile imageFile) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return new MessageResponse("Error: Email is already taken!");
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            return new MessageResponse("Error: Username is already taken!");
        }

        // Save the image to a directory and set its path
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imagePath = saveImage(imageFile);
                user.setProfileImage(imagePath);
            } catch (IOException e) {
                return new MessageResponse("Error: Unable to save image!");
            }
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        userRepository.save(user);

        return new MessageResponse("User registered successfully!");
    }

    private String saveImage(MultipartFile imageFile) throws IOException {
        String uploadDir = "path/to/image/directory/"; // Replace with your image folder path
        String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        Path filePath = Paths.get(uploadDir + fileName);
        Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
        return filePath.toString();
    }

    // Find user by email
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    // Find user by username
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    // Find user by ID
    public Optional<User> findUserById(Long userId) {
        return userRepository.findById(userId);
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
