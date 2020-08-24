package com.bixi.crud.reflect;

import com.bixi.crud.config.BixiEntity;
import com.bixi.crud.controller.impl.BaseControllerImpl;
import com.bixi.crud.service.impl.BaseServiceImpl;
import com.bixi.crud.utils.NameUtils;
import com.bixi.utils.BixiReflectionUtils;
import org.springframework.aop.TargetSource;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaMetamodelEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import java.beans.Introspector;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class JpaRepositoryAutoRegister implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void detect(){
        this.autoRegistRepository();
    }

    private void autoRegistRepository(){
        List<Class> entitiesInSpringContainer = getDetachedEntities();
        EntityManager em=this.applicationContext.getBean(EntityManager.class);
        entitiesInSpringContainer.stream().forEach(autoEnhanceEntity->{
            registRepository(em, autoEnhanceEntity);
            registService(autoEnhanceEntity);
            registController(autoEnhanceEntity);
        });
    }

    private void registController(Class autoEnhanceEntity) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)this.applicationContext;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        BeanDefinitionBuilder builder= BeanDefinitionBuilder.genericBeanDefinition(BaseControllerImpl.class);
        builder=builder.addPropertyReference("baseService",NameUtils.generateServiceNameByEntity(autoEnhanceEntity));
        String beanName= NameUtils.generateControllerNameByEntity(autoEnhanceEntity);
        defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());

    }

    private void registService(Class autoEnhanceEntity) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)this.applicationContext;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        BeanDefinitionBuilder builder= BeanDefinitionBuilder.genericBeanDefinition(BaseServiceImpl.class);
        builder=builder.addPropertyReference("jpaRepositoryImplementation",NameUtils.generateRepositoryNameByEntity(autoEnhanceEntity));
        String beanName= NameUtils.generateServiceNameByEntity(autoEnhanceEntity);
        defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
    }

    private void registRepository(EntityManager em, Class autoEnhanceEntity) {
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext)this.applicationContext;
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        BeanDefinitionBuilder builder= BeanDefinitionBuilder.genericBeanDefinition(SimpleJpaRepository.class);
        builder=builder.addConstructorArgValue(autoEnhanceEntity);
        builder=builder.addConstructorArgValue(em);
        String beanName= NameUtils.generateRepositoryNameByEntity(autoEnhanceEntity);
        defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
    }

    private List<Class> getDetachedEntities() {
        List<Class> entitiesInSpringContainer=this.getAllEntitiesInSpringContainer();
        List<Class> customEntities= this.detectAllEntitiesWithCustomRepository();
        entitiesInSpringContainer.removeAll(customEntities);
        return entitiesInSpringContainer;
    }

    private List<Class> getAllEntitiesInSpringContainer(){
        List<Class> entityNames=new ArrayList<>();
        Map<String, Object> map =  this.applicationContext.getBeansWithAnnotation(BixiEntity.class);
        map.values().stream().forEach(entity->{
            entityNames.add(entity.getClass());
        });
        return entityNames;
    }

    private List<Class> detectAllEntitiesWithCustomRepository() {
        Map<String,JpaRepository> jpaRepositoryMap= this.applicationContext.getBeansOfType(JpaRepository.class);
        List<JpaRepository> jpaRepositories = jpaRepositoryMap.values().stream().collect(Collectors.toList());
        List<Class> custormEntityNames=new ArrayList<>();
        jpaRepositories.stream().forEach(jpaRepository -> {
            Proxy proxy=(Proxy)jpaRepository;
            Object hObject= BixiReflectionUtils.getFieldValue(proxy,"h");
            ProxyFactory advisedObject= BixiReflectionUtils.getFieldValue(hObject,"advised");
            TargetSource targetSource = advisedObject.getTargetSource();
            try {
                Object object= targetSource.getTarget();
                if(object instanceof SimpleJpaRepository){
                    SimpleJpaRepository simpleJpaRepository= (SimpleJpaRepository) object;
                    JpaMetamodelEntityInformation jpaMetamodelEntityInformation= BixiReflectionUtils.getFieldValue(simpleJpaRepository,"entityInformation");
                    Class clazz= BixiReflectionUtils.getFieldValue(jpaMetamodelEntityInformation,"domainClass");
                    custormEntityNames.add(clazz);
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
