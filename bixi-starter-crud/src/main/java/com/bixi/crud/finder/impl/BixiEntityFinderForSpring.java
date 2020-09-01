package com.bixi.crud.finder.impl;

import com.bixi.crud.config.BixiEntity;
import com.bixi.crud.finder.BixiEntityFinder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Description: finder for detect entities in spring
 * @author cao xueliang
 * @date 2020/9/19:03
*/
@Component
public class BixiEntityFinderForSpring implements BixiEntityFinder, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public List<Class> findAll() {
        List<Class> entitys=new ArrayList<>();
        Map<String, Object> map =  this.applicationContext.getBeansWithAnnotation(BixiEntity.class);
        map.values().stream().forEach(entity->{
            entitys.add(entity.getClass());
        });
        return entitys;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
