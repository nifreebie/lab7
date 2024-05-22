package org.example.server.command_handlers;

import org.example.contract.command.HelpCommand;
import org.example.contract.responses.Response;
import org.example.contract.responses.ResponseWithMessage;
import org.example.contract.utils.StatusCode;

public class HelpCommandHandler extends CommandHandler<HelpCommand>{
    @Override
    public Response handle(HelpCommand command) {
        String helpOutput = "";
        helpOutput += "add: добавить новый элемент в коллекцию\n" +
                "add_if_min: добавить новый элемент в коллекцию, если его значение меньше, чем у наименьшего элемента этой коллекции\n" +
                "clear: очистить коллекцию\n" +
                "execute_script: считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\n" +
                "exit: завершить программу (без сохранения в файл)\n" +
                "help: вывести справку по доступным командам\n" +
                "info: вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
                "print_descending: вывести элементы коллекции в порядке убывания\n" +
                "print_field_descending_part_number: вывести значения поля partNumber всех элементов в порядке убывания\n" +
                "remove_by_id: удалить элемент из коллекции по его id\n" +
                "remove_greater: удалить из коллекции все элементы, превышающие заданный\n" +
                "remove_lower: удалить из коллекции все элементы, меньшие, чем заданный\n" +
                "show: вывести в стандартный поток вывода все элементы коллекции в строковом представлении\n" +
                "update: обновить значение элемента коллекции, id которого равен заданному";
        return new ResponseWithMessage(StatusCode._200_SUCCESS_, helpOutput);
    }
}
