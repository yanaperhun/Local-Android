package com.local.app.api.errors;

import java.io.IOException;

public class ServerErrorException extends IOException {
    private final ServerError error;

    public ServerErrorException(ServerError error) {
        this.error = error;
    }

    @Override
    public String getMessage() {
        return error.getMessage();
    }

    public int getCode() {
        return error.code;
    }
}
