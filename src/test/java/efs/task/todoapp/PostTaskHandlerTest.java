package efs.task.todoapp;

import efs.task.todoapp.util.ToDoServerExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;

import static java.net.http.HttpResponse.BodyHandlers.ofString;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ToDoServerExtension.class)
public class PostTaskHandlerTest {
    public static final String TODO_APP_PATH = "http://localhost:8080/todo/";

    private HttpClient httpClient;

    @BeforeEach
    void setUp() {
        httpClient = HttpClient.newHttpClient();
    }

    @Test
    @Timeout(1)
    void shouldReturnCreatedStatusForGivenTask() throws IOException, InterruptedException {
        //given
        String body = "{'username': 'janKowalski','password': 'am!sK#123'}";
        var httpRequest = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH + "user"))
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        var httpResponse = httpClient.send(httpRequest, ofString());

        String body1 = "{'description': 'Kup mleko', 'due': '2021-06-30'}";
        var httpRequest1 = HttpRequest.newBuilder()
                .uri(URI.create(TODO_APP_PATH + "task"))
                .setHeader("auth", "amFuS293YWxza2k=:YW0hc0sjMTIz")
                .version(HttpClient.Version.HTTP_1_1)
                .POST(HttpRequest.BodyPublishers.ofString(body1))
                .build();

        //when
        var httpResponse1 = httpClient.send(httpRequest1, ofString());
        System.out.println(httpResponse1.body());


        //then
        assertThat(httpResponse1.statusCode()).as("Response status code").isEqualTo(201);

    }
}
