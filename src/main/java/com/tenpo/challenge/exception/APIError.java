package com.tenpo.challenge.exception;

import java.util.List;

public record APIError(String message, List<String> details) {
    public APIError(String message, String detail) {
        this(message, List.of(detail));
    }
}
