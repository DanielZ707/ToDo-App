package efs.task.todoapp.service.handlers.user;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class OptionsUserHandler {

    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
