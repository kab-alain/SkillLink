package kab.auca.skilllink.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String username; // New username field
    private String name;
    private String email;
    private String password;
    private String role; // Learner, Instructor, Admin
    private String skills; // Comma-separated or use a many-to-many relation
    private String profileImage;
    private String otp;

    // No-args constructor (required by JPA)
    public User() {
    }

    // Constructor with userId
    public User(Long userId) {
        this.userId = userId;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getProfileImage() {
        // Base URL for serving images
        String baseUrl = "http://localhost:8080/uploads/";

        // Return the full URL if profileImage is not null, otherwise return null
        return profileImage != null ? baseUrl + profileImage : null;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }
    // Add this field in the User class

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

}
