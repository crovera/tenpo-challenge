package com.tenpo.challenge.calculator;

import org.springframework.stereotype.Service;

@Service
public class CalcService {
    public Result add(double n1, double n2) {
        return new Result(n1 + n2);
    }
}
