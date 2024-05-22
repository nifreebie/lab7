package org.example.contract.responses;

import lombok.Getter;
import org.example.contract.utils.StatusCode;

import java.util.List;
@Getter
public class ExecuteScriptResponse extends Response{
    List<Response> responseList;

    public ExecuteScriptResponse(StatusCode statusCode, List<Response> responseList) {
        super(statusCode);
        this.responseList = responseList;
    }
}
