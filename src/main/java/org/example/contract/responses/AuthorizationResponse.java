package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.model.User;
import org.example.contract.utils.StatusCode;
@Getter
public class AuthorizationResponse extends Response{
    private String message;
    private User user;
    public AuthorizationResponse(StatusCode statusCode, String message, User user) {
        super(statusCode);
        this.message = message;
        this.user = user;
    }
}
