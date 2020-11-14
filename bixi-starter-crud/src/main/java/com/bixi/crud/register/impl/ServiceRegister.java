package com.bixi.crud.register.impl;

import com.bixi.crud.template.service.impl.BaseServiceImpl;
import com.bixi.crud.utils.NameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author cao xueliang
 * @Description: regist service for entity
 * @date 2020/9/19:58
 */
@Order(10)
public class ServiceRegister extends AbsBeanRegister {

    @Value("${com.bixi.crud.template.service.impl.BaseServiceImpl.property.JpaRepositoryImplementation.name}")
    private String baseServicePropertyName;

    @Value("${com.bixi.crud.template.service.impl.BaseServiceImpl.property.entityClazz}")
    private String baseServicePropertyEntityClazz;

    @Value("${com.bixi.crud.register.impl.ServiceRegister.target}")
    private String target;

    @Override
    protected BeanDefinitionBuilder getBeanDefinitionBuilder(Class entityClass) {
        try {
            Class clazz = Class.forName(this.target);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(clazz);
            builder = builder.addPropertyReference(baseServicePropertyName, NameUtils.generateRepositoryNameByEntity(entityClass));
            builder = builder.addPropertyValue(this.baseServicePropertyEntityClazz, entityClass);
            return builder;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getBeanName(Class entityClass) {
        return NameUtils.generateServiceNameByEntity(entityClass);
    }
}
