package com.bixi.crud.utils;

import java.beans.Introspector;

public class NameUtils {
    public static String generateRepositoryNameByEntity(Class entityClass){
        return Introspector.decapitalize(entityClass.getSimpleName()+"Repository");
    }

    public static String generateServiceNameByEntity(Class entityClass){
        return Introspector.decapitalize(entityClass.getSimpleName()+"Service");
    }

    public static String generateControllerNameByEntity(Class entityClass){
        return Introspector.decapitalize(entityClass.getSimpleName()+"Controller");
    }

    public static String generateControllerURLByEntity(Class entityClass){
        return Introspector.decapitalize(entityClass.getSimpleName());
    }
}
