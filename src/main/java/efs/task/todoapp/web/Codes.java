package efs.task.todoapp.web;

public enum Codes {
    OK(200), BadRequest(400), Unauthorized(401), Conflict(409), NotFound(404), Forbidden(403), Created(201);

    private int number;

    Codes(int i) {
        number = i;
    }

    public int getNumber() {
        return number;
    }
}
