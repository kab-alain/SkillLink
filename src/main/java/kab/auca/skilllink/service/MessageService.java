package kab.auca.skilllink.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kab.auca.skilllink.model.Messages;
import kab.auca.skilllink.repository.MessageRepository;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    // Send message
    public Messages sendMessage(Messages message) {
        message.setTimestamp(LocalDateTime.now()); // Set the current time when the message is sent
        return messageRepository.save(message);
    }

    // Get messages by sender ID
    public List<Messages> getMessagesBySender(Long senderId) {
        return messageRepository.findBySenderId(senderId);
    }

    // Get messages by receiver ID
    public List<Messages> getMessagesByReceiver(Long receiverId) {
        return messageRepository.findByReceiverId(receiverId);
    }

    // Delete a message by ID
    public void deleteMessage(Long messageId) {
        messageRepository.deleteById(messageId);
    }
}
