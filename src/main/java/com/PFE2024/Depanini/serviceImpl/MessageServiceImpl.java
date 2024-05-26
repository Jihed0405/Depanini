package com.PFE2024.Depanini.serviceImpl;

import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.repository.MessageRepository;
import com.PFE2024.Depanini.service.MessageService;

@Service
public class MessageServiceImpl implements MessageService {
    private final MessageRepository messageRepository;

    public MessageServiceImpl(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message saveMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesBetweenUsers(Long senderId, Long receiverId) {
        return messageRepository.findMessagesBetweenUsers(senderId, receiverId);
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public List<Message> getUserMessages(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    public void updateSeenDate(List<Long> messageIds, Long userId) {

        List<Message> messages = messageRepository.findAllById(messageIds);

        Date currentDate = new Date();
        for (Message message : messages) {

            if (message.getReceiver().getId().equals(userId)) {

                if (message.getSeenDate() == null) {
                    message.setSeenDate(currentDate);
                }
            }
        }

        messageRepository.saveAll(messages);
    }
}
