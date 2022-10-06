package efs.task.todoapp.service.handlers.task;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import efs.task.todoapp.repository.TaskEntity;
import efs.task.todoapp.repository.UserEntity;
import efs.task.todoapp.service.ToDoService;
import efs.task.todoapp.service.handlers.IncorrectData;
import efs.task.todoapp.service.handlers.NoUserOrWrongPassword;
import efs.task.todoapp.web.Codes;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;
import java.util.List;

public class GetTasksHandler {
    public void handle(HttpExchange exchange, ToDoService service) throws IOException {
        try {
            Headers headers = exchange.getRequestHeaders();

            String headerString = headers.getFirst("auth");
            if (headerString == null) {
                throw new IncorrectData();
            }
            String[] splitted = headerString.split(":");
            if (splitted.length != 2) {
                throw new IncorrectData();
            }
            byte[] decodedHeader = Base64.getDecoder().decode(splitted[0]);
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("username", new String(decodedHeader));
            byte[] decodedHeader1 = Base64.getDecoder().decode(splitted[1]);
            jsonObject.addProperty("password", new String(decodedHeader1));
            String json = new Gson().toJson(jsonObject);
            UserEntity userEntity = new Gson().fromJson(json, UserEntity.class);
            List<TaskEntity> taskEntityList = service.GetTasks(userEntity);
            JsonArray jsonArray = new JsonArray();
            for (TaskEntity taskEntity : taskEntityList) {
                JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("id", taskEntity.getUuid().toString());
                jsonObject1.addProperty("description", taskEntity.getTaskJSON().getDescription());
                jsonObject1.addProperty("due", taskEntity.getTaskJSON().getDue());
                jsonArray.add(jsonObject1);
            }
            String json1 = new Gson().toJson(jsonArray);
            exchange.sendResponseHeaders(Codes.OK.getNumber(), json1.length());
            OutputStream os = exchange.getResponseBody();
            os.write(json1.getBytes());
            os.close();
        } catch (IncorrectData | IllegalArgumentException incorrectData) {
            exchange.sendResponseHeaders(Codes.BadRequest.getNumber(), 0);
        } catch (NoUserOrWrongPassword noUserOrWrongPassword) {
            exchange.sendResponseHeaders(Codes.Unauthorized.getNumber(), 0);
        }
        exchange.close();
    }
}
