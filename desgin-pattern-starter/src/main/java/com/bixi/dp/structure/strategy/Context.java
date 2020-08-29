package com.bixi.dp.structure.strategy;

import com.bixi.dp.structure.strategy.condition.Condition;
import com.bixi.dp.structure.strategy.strategy.Strategy;
import lombok.Data;

@Data
public class Context {
    private Condition condition;

    private Strategy strategy;
}
