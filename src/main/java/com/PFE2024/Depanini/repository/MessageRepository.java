package com.PFE2024.Depanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.PFE2024.Depanini.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    @Query("SELECT m FROM Message m WHERE (m.sender.id = :id1 AND m.receiver.id = :id2) OR (m.sender.id = :id2 AND m.receiver.id = :id1)")
    List<Message> findMessagesBetweenUsers(@Param("id1") Long id1, @Param("id2") Long id2);
}