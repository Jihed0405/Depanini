package com.PFE2024.Depanini.model.dto;

import com.PFE2024.Depanini.model.Message;
import com.PFE2024.Depanini.model.MessageType;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data

public class MessageDTO {
    @NotNull(message = "sender ID is required")
    private Long senderId;
    @NotNull(message = "User ID is required")
    private Long receiverId;
    private String content;
    private MessageType messageType;

}
