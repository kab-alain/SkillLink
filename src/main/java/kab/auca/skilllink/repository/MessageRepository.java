package kab.auca.skilllink.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import kab.auca.skilllink.model.Messages;

@Repository
public interface MessageRepository extends JpaRepository<Messages, Long> {
    List<Messages> findBySender_UserId(Long senderId);
List<Messages> findByReceiver_UserId(Long receiverId);
}
