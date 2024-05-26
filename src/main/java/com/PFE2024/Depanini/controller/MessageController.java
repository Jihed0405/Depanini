package com.PFE2024.Depanini.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.PFE2024.Depanini.model.Category;
import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.model.MessageType;
import com.PFE2024.Depanini.model.User;
import com.PFE2024.Depanini.model.dto.MessageDTO;
import com.PFE2024.Depanini.request.SeenDateUpdateRequest;
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
            @RequestPart(name = "file", required = false) MultipartFile file) throws IOException {
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
        if (file != null && !file.isEmpty()) {
            if (messageDTO.getMessageType() == MessageType.MEDIA)
                filePath = saveFile(file, messageDTO.getSenderId(), messageDTO.getReceiverId());
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

    @GetMapping("/all")
    public ResponseEntity<List<Message>> getAllMessages() {
        List<Message> messages = messageService.getAllMessages();
        return ResponseEntity.ok(messages);
    }

    @GetMapping("/files/{senderId}_{receiverId}/{fileName}")
    public ResponseEntity<Resource> getFile(@PathVariable Long senderId, @PathVariable Long receiverId,
            @PathVariable String fileName) throws IOException {

        Path filePath = Paths.get("uploads/" + senderId + "_" + receiverId + "/" + fileName);
        Resource resource = new UrlResource(filePath.toUri());

        if (resource.exists() || resource.isReadable()) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                    .body(resource);
        } else {
            throw new RuntimeException("Could not read the file!");
        }
    }

    @GetMapping("/user-messages")
    public List<Message> getUserMessages(@RequestParam Long userId) {
        return messageService.getUserMessages(userId);
    }

    @PostMapping("/updateSeenDate")
    public ResponseEntity<Void> updateSeenDate(@RequestBody SeenDateUpdateRequest request) {
        List<Long> messageIds = request.getMessageIds();
        Long userId = request.getUserId();

        // Call the service method to update the seen date
        messageService.updateSeenDate(messageIds, userId);

        return ResponseEntity.ok().build();
    }

    private String saveFile(MultipartFile file, Long senderId, Long receiverId) throws IOException {
        String uploadDir = "uploads/" + senderId + "_" + receiverId + "/";
        uploadDir = uploadDir.replace("\\", "/");
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        String fileName = file.getOriginalFilename();
        logger.info("Received file with name: {}", fileName);
        Path filePath = uploadPath.resolve(fileName);
        logger.info("Saving file to path: {}", filePath.toString());
        file.transferTo(filePath);
        String normalizedPath = filePath.toString().replace("\\", "/");
        logger.info("Normalized file path: {}", normalizedPath);
        return normalizedPath;
    }

}
