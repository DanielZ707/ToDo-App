package efs.task.todoapp;

import com.sun.net.httpserver.HttpServer;
import efs.task.todoapp.web.WebServerFactory;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ToDoApplication {
    private static final Logger LOGGER = Logger.getLogger(ToDoApplication.class.getName());

    public static void main(String[] args) {
        var application = new ToDoApplication();
        var server = application.createServer();
        server.start();

        LOGGER.info("The Application's server has started!");
    }

    public HttpServer createServer() {
        try {
            return WebServerFactory.createServer();
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "The Web server cannot be started.", e);
            throw new IllegalStateException();
        }
    }
}
