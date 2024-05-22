package org.example.contract.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.contract.utils.StatusCode;

import java.io.Serializable;
@Getter
@Setter
@AllArgsConstructor
public abstract class Response implements Serializable {
    private StatusCode statusCode;

}
