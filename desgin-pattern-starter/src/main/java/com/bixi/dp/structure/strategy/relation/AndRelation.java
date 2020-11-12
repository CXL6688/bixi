package com.bixi.dp.structure.strategy.relation;

import com.bixi.dp.structure.strategy.condition.Condition;

import java.util.List;

public final class AndRelation extends AbsRelation {

    @Override
    public <T> boolean with(List<Condition> conditions, T t) {
        for (Condition condition : conditions) {
            if (!condition.isMatch(t)) {
                return false;
            }
        }
        return true;
    }
}
