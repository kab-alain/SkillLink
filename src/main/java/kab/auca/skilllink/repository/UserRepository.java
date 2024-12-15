package kab.auca.skilllink.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Custom query to search users by name (case-insensitive)
    List<User> findByNameContainingIgnoreCase(String name);

    // Check if a user exists by email
    boolean existsByEmail(String email);

    boolean existsByUsername(String name);

    // Find a user by email
    Optional<User> findByEmail(String email);

    Optional<User> findByUsername(String username);

    // Count users by role
    long countByRole(String role);

    // Find all users with a specific role
    List<User> findByRole(String role);

    @Query("SELECT u FROM User u WHERE u.name LIKE %:query%")
    List<User> searchByName(@Param("query") String query);

}
