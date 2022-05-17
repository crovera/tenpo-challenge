package com.tenpo.challenge.session;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(name = "Authentication")
@RestController
public class SessionController {
    @Operation(summary = "Login")
    @PostMapping("/api/login")
    public ResponseEntity<Login> fakeLogin(@Parameter(description = "Credentials") @Valid @RequestBody Credentials credentials) {
        throw new IllegalStateException("This method is just for documentation. It's overridden by Spring Security filters.");
    }

    @Operation(summary = "Logout")
    @PostMapping("/api/logout")
    public ResponseEntity<Logout> fakeLogout(@RequestHeader(value = "Authorization") String headerAuth) {
        throw new IllegalStateException("This method is just for documentation. It's overridden by Spring Security filters.");
    }
}
