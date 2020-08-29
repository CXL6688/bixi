package com.bixi.dp.structure.strategy.condition;

import lombok.Data;

import java.util.Map;

@Data
public class TimeCondition extends AbsCondition {
    private Long startTime;
    private Long endTime;

    @Override
    public <T> boolean matches(T t) {
        if(!(t instanceof Map)){
            return false;
        }
        Map map= (Map) t;
        Long eventTime= (Long) map.get("time");
        if(eventTime>= startTime && eventTime<=endTime){
            return true;
        }
        return false;
    }
}
