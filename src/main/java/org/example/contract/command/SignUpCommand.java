package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.model.User;

@AllArgsConstructor
@Getter
public class SignUpCommand implements Command {
    private User user;
}
