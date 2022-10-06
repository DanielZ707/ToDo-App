package efs.task.todoapp.service.handlers.user;

import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.repository.DuplicatedData;
import efs.task.todoapp.service.ToDoService;
import efs.task.todoapp.service.handlers.IncorrectData;
import efs.task.todoapp.web.Codes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.stream.Collectors;

public class PostUserHandler {

    public void handle(HttpExchange exchange, ToDoService service) throws IOException {
        String user = new BufferedReader(new InputStreamReader(exchange.getRequestBody()))
                .lines().collect(Collectors.joining("\n"));

        try {
            String response = service.UserSave(user);
            exchange.sendResponseHeaders(Codes.Created.getNumber(), response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();

        } catch (DuplicatedData duplicatedData) {
            exchange.sendResponseHeaders(Codes.Conflict.getNumber(), 0);
        } catch (IncorrectData incorrectData) {
            exchange.sendResponseHeaders(Codes.BadRequest.getNumber(), 0);
        }
        exchange.close();
    }

}
