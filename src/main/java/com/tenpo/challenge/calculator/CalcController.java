package com.tenpo.challenge.calculator;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;

@Tag(name = "Calculator")
@RestController
@AllArgsConstructor
@RequestMapping("/api/calc")
public class CalcController {
    private final CalcService calcService;

    @GetMapping("/add")
    @Operation(summary = "Addition")
    public ResponseEntity<Result> add(
            @RequestHeader(value = "Authorization") String headerAuth,
            @RequestParam() @NotEmpty double numberA,
            @RequestParam() @NotEmpty double numberB
    ) {
        return ResponseEntity.ok(calcService.add(numberA, numberB));
    }
}
