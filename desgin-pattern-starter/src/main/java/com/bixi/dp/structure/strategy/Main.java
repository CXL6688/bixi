package com.bixi.dp.structure.strategy;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext= new ClassPathXmlApplicationContext("application.xml");
        Map<String, Context> contextMap= applicationContext.getBeansOfType(Context.class);

        Map userInfo=new HashMap();
        userInfo.put("name","小张");
        userInfo.put("time",1598666400000L);
        userInfo.put("location","廊坊师范学院");

        contextMap.values().stream().forEach(context -> {
            if(context.getCondition().isMatch(userInfo)){
                Object result=context.getStrategy().doExecute();
                System.out.println(result);
            }
        });
    }
}
