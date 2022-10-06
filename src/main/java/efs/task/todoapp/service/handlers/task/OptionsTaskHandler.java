package efs.task.todoapp.service.handlers.task;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class OptionsTaskHandler {
    public void handle(HttpExchange exchange) throws IOException {
        exchange.sendResponseHeaders(200, 0);
        exchange.close();
    }
}
