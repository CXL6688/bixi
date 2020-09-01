package com.bixi.crud.register;

import com.bixi.crud.config.BixiEntity;
import com.bixi.crud.template.controller.impl.BaseControllerImpl;
import com.bixi.crud.template.service.impl.BaseServiceImpl;
import com.bixi.crud.utils.NameUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.persistence.EntityManager;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class JpaRepositoryAutoRegister implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    public void detect(){
        this.autoRegistRepository();
    }

    private void autoRegistRepository(){
        List<Class> entitiesInSpringContainer = getAllEntitiesWithBixiAnnotation();
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
        String controllerBeanName= NameUtils.generateControllerNameByEntity(autoEnhanceEntity);
        defaultListableBeanFactory.registerBeanDefinition(controllerBeanName, builder.getRawBeanDefinition());

        Map<String, RequestMappingHandlerMapping> map = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        Optional<RequestMappingHandlerMapping> optional= map.values().stream().findFirst();
        RequestMappingHandlerMapping requestMappingHandlerMapping=optional.get();
        RequestMappingInfo requestMappingInfo = RequestMappingInfo
                .paths("/"+NameUtils.generateControllerURLByEntity(autoEnhanceEntity)+"/all")
                .methods(RequestMethod.GET)
                .build();
        Object controller=this.applicationContext.getBean(controllerBeanName);
        try {
            Method method = BaseControllerImpl.class.getMethod("queryAll",new Class[]{});
            requestMappingHandlerMapping.registerMapping(requestMappingInfo,controller,method);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }

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

    private List<Class> getAllEntitiesWithBixiAnnotation(){
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
