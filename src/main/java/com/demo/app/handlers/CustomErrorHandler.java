package com.demo.app.handlers;

import com.demo.app.configurations.InternalizationConfiguration;
import com.demo.app.models.exceptions.CustomException;
import com.demo.app.models.exceptions.CustomExceptionErrorResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Locale;

@RestControllerAdvice
@Slf4j
@AllArgsConstructor
public class CustomErrorHandler {

    private InternalizationConfiguration internalizationConfiguration;

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CustomExceptionErrorResponse> customHandler(CustomException exception) {
        log.error("Custom error message: " + exception.getReason());

        CustomExceptionErrorResponse customExceptionErrorResponse = CustomExceptionErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(exception.getStatus().value())
                .statusName(exception.getStatus().name())
                .error(getMessage(exception.getReason()))
                .build();

        return new ResponseEntity<>(customExceptionErrorResponse, exception.getStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CustomExceptionErrorResponse> customHandler(Exception exception) {
        log.error("Exception message: " + exception.getMessage());

        CustomExceptionErrorResponse customExceptionErrorResponse = CustomExceptionErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .statusCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .statusName(HttpStatus.INTERNAL_SERVER_ERROR.name())
                .error(getMessage("custom.error.default"))
                .build();

        return new ResponseEntity<>(customExceptionErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private String getMessage(String code) {
        return internalizationConfiguration.messageSource().getMessage(code, null, Locale.getDefault());
    }

}
