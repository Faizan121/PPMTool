package io.faizanUddin.ppmtool.exceptions;

public class UserAlreadyExistExceptionResponse {

    private String username;

    public UserAlreadyExistExceptionResponse(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
