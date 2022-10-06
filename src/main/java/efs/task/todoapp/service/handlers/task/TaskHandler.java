package efs.task.todoapp.service.handlers.task;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import efs.task.todoapp.service.ToDoService;

import java.io.IOException;

public class TaskHandler implements HttpHandler {
    ToDoService service;

    public TaskHandler(ToDoService service) {
        this.service = service;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Auth");
        switch (exchange.getRequestMethod()) {
            case "OPTIONS":
                new OptionsTaskHandler().handle(exchange);
                break;
            case "DELETE":
                new DeleteTaskHandler().handle(exchange, service);
                break;
            case "PUT":
                new PutTaskHandler().handle(exchange, service);
                break;
            case "GET":
                if (exchange.getRequestURI().getPath().equals("/todo/task")) {
                    new GetTasksHandler().handle(exchange, service);
                } else {
                    new GetOneTaskHandler().handle(exchange, service);
                }
                break;
            case "POST":
                new PostTaskHandler().handle(exchange, service);
                break;
        }

    }
}
