package kab.auca.skilllink.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kab.auca.skilllink.model.Notifications;
import kab.auca.skilllink.response.MessageResponse;
import kab.auca.skilllink.service.NotificationsService;

@RestController
@RequestMapping("/api/notifications")
@CrossOrigin("*")
public class NotificationsController {

    @Autowired
    private NotificationsService notificationsService;

    // Create a new notification
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> createNotification(@RequestBody Notifications notification) {
        MessageResponse response = notificationsService.createNotification(notification);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Get notifications by user ID
    @GetMapping(value = "/getUserNotifications/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notifications>> getNotificationsByUserId(@PathVariable Long userId) {
        List<Notifications> notifications = notificationsService.getNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    // Get unread notifications by user ID
    @GetMapping(value = "/getUnreadNotifications/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Notifications>> getUnreadNotifications(@PathVariable Long userId) {
        List<Notifications> notifications = notificationsService.getUnreadNotifications(userId);
        return ResponseEntity.ok(notifications);
    }

    // Update notification status (e.g., from Unread to Read)
    @PutMapping(value = "/updateStatus/{notificationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<MessageResponse> updateStatus(@PathVariable Long notificationId, @RequestBody String status) {
        MessageResponse response = notificationsService.updateStatus(notificationId, status);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Delete a notification
    @DeleteMapping(value = "/delete/{notificationId}")
    public ResponseEntity<MessageResponse> deleteNotification(@PathVariable Long notificationId) {
        MessageResponse response = notificationsService.deleteNotification(notificationId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(response);
    }
}
