package com.tenpo.challenge.calculator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CalcService {
    public Result add(double n1, double n2) {
        log.info("Calculating addition between: {} + {}", n1, n2);
        return new Result(n1 + n2);
    }
}
