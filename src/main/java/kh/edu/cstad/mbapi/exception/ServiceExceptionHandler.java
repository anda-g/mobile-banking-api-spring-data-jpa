package kh.edu.cstad.mbapi.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<?> handleServiceException(ResponseStatusException ex) {
        ErrorResponse<String> errorResponse = ErrorResponse.<String>builder()
                .message("Business logic error")
                .status(ex.getStatusCode().value())
                .timestamp(LocalDateTime.now())
                .details(ex.getReason())
                .build();
        return ResponseEntity.status(ex.getStatusCode()).body(errorResponse);
    }
}
