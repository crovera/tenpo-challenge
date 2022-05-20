package com.tenpo.challenge.session;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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
    @PostMapping("/api/login")
    @Operation(summary = "Login")
    public ResponseEntity<Login> fakeLogin(@Parameter(description = "Credentials") @Valid @RequestBody Credentials credentials) {
        throw new IllegalStateException("This method is just for documentation. It's overridden by Spring Security filters.");
    }

    @PostMapping("/api/logout")
    @Operation(summary = "Logout", security = { @SecurityRequirement(name = "bearer-auth") })
    public ResponseEntity<Logout> fakeLogout() {
        throw new IllegalStateException("This method is just for documentation. It's overridden by Spring Security filters.");
    }
}
