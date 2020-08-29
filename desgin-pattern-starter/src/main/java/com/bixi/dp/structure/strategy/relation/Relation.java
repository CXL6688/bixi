package com.bixi.dp.structure.strategy.relation;

import com.bixi.dp.structure.strategy.condition.Condition;

import java.util.List;

public interface Relation {
    <T> boolean with(List<Condition> conditions,T t);
}
