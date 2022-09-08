package com.demo.app.models.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Getter
@Setter
public class CustomException extends ResponseStatusException {

    private String reason;

    public CustomException (HttpStatus status) {
        super(status);
    }

    public CustomException (HttpStatus status, String reason) {
        super(status, reason);
        this.reason = reason;
    }

}
