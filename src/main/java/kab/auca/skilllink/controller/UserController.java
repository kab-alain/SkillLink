package kab.auca.skilllink.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.UserRepository;
import kab.auca.skilllink.response.MessageResponse;

@RestController
@RequestMapping("/api/users")
@CrossOrigin("*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    // Get all users
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

    // Get user by ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Create a new user
    @PostMapping
public ResponseEntity<?> createUser(
        @RequestPart("user") String userJson, // Receive user as a JSON string
        @RequestPart(value = "imageFile", required = false) MultipartFile imageFile) {

    try {
        // Deserialize the JSON string to a User object
        ObjectMapper objectMapper = new ObjectMapper();
        User user = objectMapper.readValue(userJson, User.class);

        // Check if email or username is already taken
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: Email is already taken!"));
        }
        if (userRepository.existsByUsername(user.getUsername())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        // Handle image upload (if provided)
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = saveImage(imageFile);
            user.setProfileImage(imagePath); // Set image path in user object
        }

        // Save the new user
        User newUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    } catch (IOException e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Error: Unable to save the image."));
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new MessageResponse("Error: An unexpected error occurred."));
    }
}

private String saveImage(MultipartFile imageFile) throws IOException {
    String uploadDir = System.getProperty("user.dir") + "/uploads/";
    String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
    Path filePath = Paths.get(uploadDir + fileName);
    
    Files.createDirectories(filePath.getParent()); // Ensure the directory exists
    Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
    
    return filePath.toString(); // Return the relative file path
}
@PostMapping("/login")
public ResponseEntity<?> loginUser(@RequestBody User loginRequest) {
    // Extract username and password from the login request
    String username = loginRequest.getUsername();
    String password = loginRequest.getPassword();

    // Find the user by username
    Optional<User> userOptional = userRepository.findByUsername(username);

    if (userOptional.isPresent()) {
        User user = userOptional.get();
        
        // Check if the password matches directly (no hashing)
        if (password.equals(user.getPassword())) {
            // Create a response object containing user information and role
            Map<String, Object> response = new HashMap<>();
            response.put("userId", user.getUserId());
            response.put("username", user.getUsername());
            response.put("role", user.getRole());
            
            return ResponseEntity.ok(response); // Return user data with role
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(new MessageResponse("Error: Invalid password."));
        }
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new MessageResponse("Error: User not found."));
    }
}

    // Update user by ID
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        Optional<User> existingUser = userRepository.findById(id);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setUsername(userDetails.getUsername()); // Update username
            user.setName(userDetails.getName());
            user.setEmail(userDetails.getEmail());
            user.setPassword(userDetails.getPassword());
            user.setRole(userDetails.getRole());
            user.setSkills(userDetails.getSkills());
            user.setProfileImage(userDetails.getProfileImage());

            User updatedUser = userRepository.save(user);
            return ResponseEntity.ok(updatedUser);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Delete user by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    // Search users by name
    @GetMapping("/search")
    public ResponseEntity<List<User>> searchUsersByName(@RequestParam String name) {
        List<User> users = userRepository.findByNameContainingIgnoreCase(name);
        return ResponseEntity.ok(users);
    }

    // Search users by username
    @GetMapping("/search/username")
    public ResponseEntity<Optional<User>> searchUsersByUsername(@RequestParam String username) {
        Optional<User> users = userRepository.findByUsername(username); // Assuming this method is defined in UserRepository
        return ResponseEntity.ok(users);
    }
}
