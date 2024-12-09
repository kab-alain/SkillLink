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

    private String name;
    private String email;
    private String password;
    private String role; // Learner, Instructor, Admin
    private String skills; // Comma-separated or use a many-to-many relation
    private String profileImage;

    // Getters and Setters
}
