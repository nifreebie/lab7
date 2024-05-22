package org.example.contract.responses;

import org.example.contract.utils.StatusCode;

public class ExitResponse extends Response{
    public ExitResponse(StatusCode statusCode) {
        super(statusCode);
    }
}
