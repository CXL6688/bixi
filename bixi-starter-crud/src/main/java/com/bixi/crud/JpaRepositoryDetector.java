package com.bixi.crud;

import com.bixi.util.BixiReflectionUtil;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JpaRepositoryDetector implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void detect(){
        List<String> customEntityNames= this.detectAllEntiryNamesWithCustomRepository();

    }

    private List<String> getAllEntityNamesInSpringContainer(){
        List<String> entityNames=new ArrayList<>();
        Map<String, Object> map =  this.applicationContext.getBeansWithAnnotation(Entity.class);
        map.values().stream().forEach(entity->{
            Entity entityAnnotation= entity.getClass().getAnnotation(Entity.class);

        });
    }

    private List<String> detectAllEntiryNamesWithCustomRepository() {
        Map<String,JpaRepository> jpaRepositoryMap= this.applicationContext.getBeansOfType(JpaRepository.class);
        List<JpaRepository> jpaRepositories = jpaRepositoryMap.values().stream().collect(Collectors.toList());
        List<String> custormEntityNames=new ArrayList<>();
        jpaRepositories.stream().forEach(jpaRepository -> {
            Proxy proxy=(Proxy)jpaRepository;
            Object hObject= BixiReflectionUtil.getFieldValue(proxy,"h");
            ProxyFactory advisedObject=BixiReflectionUtil.getFieldValue(hObject,"advised");
            TargetSource targetSource = advisedObject.getTargetSource();
            try {
                Object object= targetSource.getTarget();
                if(object instanceof SimpleJpaRepository){
                    SimpleJpaRepository simpleJpaRepository= (SimpleJpaRepository) object;
                    JpaMetamodelEntityInformation jpaMetamodelEntityInformation=BixiReflectionUtil.getFieldValue(simpleJpaRepository,"entityInformation");
                    String entityName= jpaMetamodelEntityInformation.getEntityName();
                    custormEntityNames.add(entityName);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return custormEntityNames;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
