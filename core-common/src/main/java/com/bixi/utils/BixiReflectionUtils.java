package com.bixi.utils;

import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

public abstract class BixiReflectionUtils extends ReflectionUtils {
    public static <T> T getFieldValue(Object obj, String fieldName) {
        Field filed = ReflectionUtils.findField(obj.getClass(), fieldName);
        filed.setAccessible(true);
        return (T) ReflectionUtils.getField(filed, obj);
    }
}
