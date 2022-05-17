package com.tenpo.challenge.calculator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CalcServiceTest {
    private CalcService calcService;

    @BeforeEach
    void setup() {
        calcService = new CalcService();
    }

    @Test
    void add() {
        double n1 = 53;
        double n2 = 184;

        Result result = calcService.add(n1, n2);
        assertEquals(237, result.number());
    }
}
