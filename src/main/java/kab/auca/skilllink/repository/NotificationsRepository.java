package kab.auca.skilllink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.Notifications;

@Repository
public interface NotificationsRepository extends JpaRepository<Notifications, Long> {
    // Find all notifications for a specific user
    List<Notifications> findByUser_UserId(Long userId);

    // Find unread notifications for a specific user
    List<Notifications> findByUser_UserIdAndStatus(Long userId, String status);
}
