package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.model.ProductDTO;
@Getter
@AllArgsConstructor
public class AddIfMinCommand implements Command{
    private ProductDTO productDTO;
    private String login;
    private String password;
}
