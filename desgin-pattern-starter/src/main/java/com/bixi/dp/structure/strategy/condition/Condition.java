package com.bixi.dp.structure.strategy.condition;

public interface Condition {
    <T> boolean isMatch(T t);
}
