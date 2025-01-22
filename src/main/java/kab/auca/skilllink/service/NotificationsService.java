package kab.auca.skilllink.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Notifications;
import kab.auca.skilllink.model.User;
import kab.auca.skilllink.repository.NotificationsRepository;
import kab.auca.skilllink.repository.UserRepository;
import kab.auca.skilllink.response.MessageResponse;

@Service
public class NotificationsService {

    @Autowired
    private NotificationsRepository notificationsRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new notification
    public MessageResponse createNotification(Notifications notification) {
        // Validate user and userId
        if (isInvalidUser(notification.getUser())) {
            return new MessageResponse("Invalid user.");
        }

        // Validate if the user exists in the database
        if (!userRepository.existsById(notification.getUser().getUserId())) {
            return new MessageResponse("Invalid user.");
        }

        // Set additional fields and save the notification
        notification.setTimestamp(LocalDateTime.now());
        notification.setStatus("unread");
        notificationsRepository.save(notification);

        return new MessageResponse("Notification created successfully.");
    }

    private boolean isInvalidUser(User user) {
        return user == null || user.getUserId() == null;
    }

    // Get notifications by user ID
    public List<Notifications> getNotificationsByUserId(Long userId) {
        return notificationsRepository.findByUser_UserId(userId);
    }

    // Get unread notifications by user ID
    public List<Notifications> getUnreadNotifications(Long userId) {
        return notificationsRepository.findByUser_UserIdAndStatus(userId, "unread");
    }

    // Update notification status (e.g., from Unread to Read)
    public MessageResponse updateStatus(Long notificationId, String status) {
        Notifications notification = notificationsRepository.findById(notificationId).orElse(null);
        if (notification != null) {
            notification.setStatus(status);
            notificationsRepository.save(notification);
            return new MessageResponse("Notification status updated successfully.");
        } else {
            return new MessageResponse("Notification not found.");
        }
    }

    // Delete a notification
    public MessageResponse deleteNotification(Long notificationId) {
        notificationsRepository.deleteById(notificationId);
        return new MessageResponse("Notification deleted successfully.");
    }
}
