package com.bixi.dp.structure.strategy.condition;

import lombok.Data;

import java.util.Map;

@Data
public class LocationCondition extends AbsCondition {

    private String targetLocation;

    @Override
    public <T> boolean matches(T t) {
        if(!(t instanceof Map)){
            return false;
        }
        Map map= (Map) t;
        String location= (String) map.get("location");
        if(location.equals(targetLocation)){
            return true;
        }
        return false;
    }
}
