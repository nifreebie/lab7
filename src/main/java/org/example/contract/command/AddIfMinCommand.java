package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.model.ProductDTO;
import org.example.contract.model.User;

@Getter
@AllArgsConstructor
public class AddIfMinCommand implements Command{
    private ProductDTO productDTO;
    private User user;
}
