package com.bixi.crud.register.impl;

import com.bixi.crud.utils.NameUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;

/**
 * @author cao xueliang
 * @Description: regist repository for entity
 * @date 2020/9/19:26
 */
@Component
@Order(1)
public class RepositoryRegister extends AbsBeanRegister {
    private EntityManager em;
    @Value("${com.bixi.crud.register.impl.RepositoryRegister.target}")
    private String target;

    @Override
    protected BeanDefinitionBuilder getBeanDefinitionBuilder(Class entityClass) {
        try {
            Class targetClass = Class.forName(this.target);
            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(targetClass);
            builder = builder.addConstructorArgValue(entityClass);
            builder = builder.addConstructorArgValue(em);
            return builder;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected String getBeanName(Class entityClass) {
        return NameUtils.generateRepositoryNameByEntity(entityClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        super.setApplicationContext(applicationContext);
        this.em = applicationContext.getBean(EntityManager.class);
    }
}
