package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.utils.StatusCode;
@Getter
public class ResponseWithMessage extends Response{
    private String message;
    public ResponseWithMessage(StatusCode statusCode, String message) {
        super(statusCode);
        this.message = message;
    }
}
