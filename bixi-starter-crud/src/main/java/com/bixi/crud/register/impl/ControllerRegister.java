package com.bixi.crud.register.impl;

import com.bixi.crud.template.controller.impl.BaseControllerImpl;
import com.bixi.crud.utils.NameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * @author cao xueliang
 * @Description: regist controller for entity
 * @date 2020/9/1 10:07
 */
@Order(100)
public class ControllerRegister extends AbsBeanRegister {

    @Value("${com.bixi.crud.template.controller.impl.BaseControllerImpl.property.BaseService.name}")
    private String baseServiceProperty;
    @Value("${com.bixi.crud.template.controller.impl.BaseControllerImpl.property.entityClazz}")
    private String entityClazzProperty;
    @Value("${com.bixi.crud.template.controller.impl.BaseControllerImpl.property.objectMapper}")
    private String objectMapperProperty;

    @Value("${com.bixi.crud.register.impl.ControllerRegister.target}")
    private String target;

    @Override
    protected BeanDefinitionBuilder getBeanDefinitionBuilder(Class entityClass) {
        try {
            Class clazz = Class.forName(this.target);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            builder = builder.addPropertyReference(baseServiceProperty, NameUtils.generateServiceNameByEntity(entityClass));
            builder = builder.addPropertyValue(entityClazzProperty, entityClass);
            builder = builder.addAutowiredProperty(objectMapperProperty);
            return builder;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getBeanName(Class entityClass) {
        return NameUtils.generateControllerNameByEntity(entityClass);
    }

    @Override
    public boolean regist(Class entityClass) {
        super.regist(entityClass);
        registRequestMapping(entityClass);
        return true;
    }

    /**
     * @throws
     * @Description: regist mvc routers
     * @return: boolean
     * @author Cao Xueliang
     * @date 2020/9/1 10:42
     **/
    private boolean registRequestMapping(Class entityClass) {
        Map<String, RequestMappingHandlerMapping> map = applicationContext.getBeansOfType(RequestMappingHandlerMapping.class);
        Optional<RequestMappingHandlerMapping> optional = map.values().stream().findFirst();
        RequestMappingHandlerMapping requestMappingHandlerMapping = optional.get();
        String baseURL = NameUtils.generateControllerURLByEntity(entityClass);
        Object controller = this.applicationContext.getBean(this.getBeanName(entityClass));
        Method[] methods = controller.getClass().getMethods();
        for (Method method : methods) {
            RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
            if (requestMapping == null) {
                continue;
            }
            String[] requestMappingPaths = requestMapping.path();
            RequestMethod[] requestMethods = requestMapping.method();
            for (int i = 0; i < requestMappingPaths.length; i++) {
                String requestMappingPath = requestMappingPaths[i];
                requestMappingPath = "/" + baseURL + requestMappingPath;
                requestMappingPaths[i] = requestMappingPath;
            }
            RequestMappingInfo requestMappingInfo = RequestMappingInfo
                    .paths(requestMappingPaths)
                    .methods(requestMethods)
                    .build();
            requestMappingHandlerMapping.registerMapping(requestMappingInfo, controller, method);
        }
        return true;
    }
}
