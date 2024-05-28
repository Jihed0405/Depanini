package com.PFE2024.Depanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.model.User;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    @Query("SELECT m FROM Message m WHERE m.sender = ?1 OR m.receiver = ?1")
    List<Message> findMessagesByUser(User user);

    @Query("SELECT m FROM Message m WHERE (m.sender.id = :id1 AND m.receiver.id = :id2) OR (m.sender.id = :id2 AND m.receiver.id = :id1)")
    List<Message> findMessagesBetweenUsers(@Param("id1") Long id1, @Param("id2") Long id2);
}