package kab.auca.skilllink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to search users by name (case-insensitive)
    List<User> findByNameContainingIgnoreCase(String name);

    // Check if a user exists by email
    boolean existsByEmail(String email);

    // Find a user by email
    User findByEmail(String email);

    // Count users by role
    long countByRole(String role);

    // Find all users with a specific role
    List<User> findByRole(String role);
}
