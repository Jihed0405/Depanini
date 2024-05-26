package com.PFE2024.Depanini.service;

import java.util.List;

import com.PFE2024.Depanini.model.Message;

public interface MessageService {
    Message saveMessage(Message message);

    List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId);

    List<Message> getAllMessages();

    List<Message> getUserMessages(Long userId);

    void updateSeenDate(List<Long> messageIds, Long userId);
}
