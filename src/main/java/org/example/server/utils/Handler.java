package org.example.server.utils;

import org.example.contract.command.Command;
import org.example.contract.responses.Response;

@FunctionalInterface
public interface Handler {
    Response apply(Command command);
}
