package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.utils.StatusCode;
@Getter
public class AuthorizationResponse extends Response{
    private String message;
    private String login;
    private String password;
    public AuthorizationResponse(StatusCode statusCode, String message, String login, String password) {
        super(statusCode);
        this.message = message;
        this.login = login;
        this.password =password;
    }
}
