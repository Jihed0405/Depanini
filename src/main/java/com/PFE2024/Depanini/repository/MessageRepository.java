package com.PFE2024.Depanini.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.PFE2024.Depanini.model.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
