package org.example.contract.command;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.example.contract.model.User;

@Getter
@AllArgsConstructor
public class SignInCommand implements Command{
    private User user;

}
