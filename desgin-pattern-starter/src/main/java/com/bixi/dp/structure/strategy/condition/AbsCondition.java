package com.bixi.dp.structure.strategy.condition;

import com.bixi.dp.structure.strategy.relation.Relation;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
public abstract class AbsCondition implements Condition {

    protected List<Condition> conditions;

    protected Relation relation;

    @Override
    public <T> boolean isMatch(T t) {
        if(!CollectionUtils.isEmpty(conditions) && relation!=null){
            return relation.with(conditions,t);
        }else{
            return this.matches(t);
        }
    }

    public abstract <T> boolean matches(T t);
}
