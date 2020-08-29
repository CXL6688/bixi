package com.bixi.dp.structure.strategy.condition;

public class CompositeCondition extends AbsCondition{
    @Override
    public <T> boolean matches(T t) {
        return false;
    }
}
