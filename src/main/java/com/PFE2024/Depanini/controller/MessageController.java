package com.PFE2024.Depanini.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.model.MessageType;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.dto.MessageDTO;
import com.PFE2024.Depanini.service.MessageService;
import com.PFE2024.Depanini.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/messages")
public class MessageController {
    private final MessageService messageService;
    private final UserService userService;
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    public MessageController(MessageService messageService, UserService userService) {
        this.messageService = messageService;
        this.userService = userService;
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<Message> sendMessage(
            @RequestPart("messageDTO") String messageDTOStr,
            @RequestPart("file") MultipartFile file) throws IOException {
        logger.info("Received sendMessage request with MessageDTO: {}", messageDTOStr);

        // Deserialize the JSON string to MessageDTO
        ObjectMapper objectMapper = new ObjectMapper();
        MessageDTO messageDTO = objectMapper.readValue(messageDTOStr, MessageDTO.class);

        User sender = userService.getUserById(messageDTO.getSenderId());
        User receiver = userService.getUserById(messageDTO.getReceiverId());

        if (sender == null) {
            logger.error("Sender with id {} not found", messageDTO.getSenderId());
            throw new IllegalArgumentException("Sender not found");
        }

        if (receiver == null) {
            logger.error("Receiver with id {} not found", messageDTO.getReceiverId());
            throw new IllegalArgumentException("Receiver not found");
        }

        String filePath = null;
        if (!file.isEmpty()) {
            if (messageDTO.getMessageType() == MessageType.MEDIA)
                filePath = saveFile(file);
        }

        Message message = new Message();
        message.setSender(sender);
        message.setReceiver(receiver);
        message.setContent(filePath != null ? filePath : messageDTO.getContent());
        message.setTimestamp(new Date());
        message.setMessageType(messageDTO.getMessageType());

        Message createdMessage = messageService.saveMessage(message);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMessage);
    }

    @GetMapping
    public List<Message> getMessages(@RequestParam Long senderId, @RequestParam Long receiverId) {
        return messageService.getMessagesBetweenUsers(senderId, receiverId);
    }

    private String saveFile(MultipartFile file) throws IOException {
        String uploadDir = "uploads/";
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }
        String fileName = file.getOriginalFilename();
        Path filePath = uploadPath.resolve(fileName);
        file.transferTo(filePath);
        return filePath.toString();
    }
}
