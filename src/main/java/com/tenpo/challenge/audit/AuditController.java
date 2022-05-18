package com.tenpo.challenge.audit;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Audit")
@RestController
@AllArgsConstructor
@RequestMapping("/api/audit")
public class AuditController {
    private final AuditService auditService;

    @GetMapping()
    @Operation(summary = "Audit")
    public ResponseEntity<List<Entry>> getEntries(
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "15") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy
    ) {
        return ResponseEntity.ok(auditService.getEntries(pageNumber, pageSize, sortBy));
    }
}
