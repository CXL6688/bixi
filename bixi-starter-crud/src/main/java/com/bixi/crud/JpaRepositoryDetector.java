package com.bixi.crud;

import com.bixi.util.BixiReflectionUtil;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

@Component
public class JpaRepositoryDetector {
    @Autowired
    private List<JpaRepository> jpaRepositories;

    public void detect(){
        List<String> customEntityNames= this.detectAllCustomEntiryNames();
    }

    private List<String> detectAllCustomEntiryNames() {
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

    public void detect1(){

    }
}
