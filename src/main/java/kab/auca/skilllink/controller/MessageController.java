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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kab.auca.skilllink.model.Messages;
import kab.auca.skilllink.service.MessageService;

@RestController
@RequestMapping("/api/messages")
@CrossOrigin("*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    // Send message
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Messages> sendMessage(@RequestBody Messages message) {
        Messages sentMessage = messageService.sendMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(sentMessage);
    }

    // Get messages by sender ID
    @GetMapping(value = "/sender/{senderId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Messages>> getMessagesBySender(@PathVariable Long senderId) {
        List<Messages> messages = messageService.getMessagesBySender(senderId);
        return ResponseEntity.ok(messages);
    }

    // Get messages by receiver ID
    @GetMapping(value = "/receiver/{receiverId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Messages>> getMessagesByReceiver(@PathVariable Long receiverId) {
        List<Messages> messages = messageService.getMessagesByReceiver(receiverId);
        return ResponseEntity.ok(messages);
    }

    // Delete message by ID
    @DeleteMapping(value = "/delete/{messageId}")
    public ResponseEntity<Void> deleteMessage(@PathVariable Long messageId) {
        messageService.deleteMessage(messageId);
        return ResponseEntity.noContent().build();
    }
}
