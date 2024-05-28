package com.PFE2024.Depanini.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.util.Arrays;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CategoryHasServicesException.class)
    public ResponseEntity<?> handleCategoryHasServicesException(CategoryHasServicesException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<?> handleCategoryNotFoundException(EntityNotFoundException ex, WebRequest request) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<String> handleDataIntegrityViolation(DataIntegrityViolationException ex) {
        // Check if the exception message contains the specific constraint violation
        // message
        if (ex.getMessage().contains("fkjcc75fyoomg787t6gxtw2y18k")) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Could not delete the service because it is still referenced by other entities.");
        } else {
            // Attempt to extract and sort messages
            String errorMessage;
            try {
                errorMessage = extractAndSortMessages(ex.getMessage());
            } catch (Exception e) {
                errorMessage = ex.getMessage();
            }
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request: " + errorMessage);
        }
    }

    // Utility method to extract and sort messages
    private String extractAndSortMessages(String message) {
        if (message == null || message.isEmpty()) {
            return "No detailed message available.";
        }

        String[] messageParts = message.split("[\\[\\]]");
        if (messageParts.length > 1) {
            String[] errors = messageParts[1].split(", ");
            Arrays.sort(errors);
            return Arrays.stream(errors)
                    .map(error -> error.contains(": ") ? error.split(": ")[1] : error)
                    .collect(Collectors.joining(", "));
        }

        return message;
    }

    // Other exception handlers...
}
