package com.bixi.crud.register.impl;

import com.bixi.crud.register.BeanRegister;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author cao xueliang
 * @Description: abstract register class for regist beans
 * @date 2020/9/19:31
 */
public abstract class AbsBeanRegister implements ApplicationContextAware, BeanRegister {
    protected ApplicationContext applicationContext;
    protected DefaultListableBeanFactory defaultListableBeanFactory;

    /**
     * @param entityClass: bixi entity class
     * @throws
     * @Description: do regist bean by entity class
     * @return: boolean
     * @author Cao Xueliang
     * @date 2020/9/1 10:00
     **/
    @Override
    public boolean regist(Class entityClass) {
        try {
            BeanDefinitionBuilder builder = this.getBeanDefinitionBuilder(entityClass);
            String beanName = this.getBeanName(entityClass);
            defaultListableBeanFactory.registerBeanDefinition(beanName, builder.getRawBeanDefinition());
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param entityClass:
     * @throws
     * @Description: get beanDefinitionBuilder for different beans
     * @return: org.springframework.beans.factory.support.BeanDefinitionBuilder
     * @author Cao Xueliang
     * @date 2020/9/1 10:00
     **/
    protected abstract BeanDefinitionBuilder getBeanDefinitionBuilder(Class entityClass);

    /**
     * @param entityClass:
     * @throws
     * @Description: get bean name to regist
     * @return: java.lang.String
     * @author Cao Xueliang
     * @date 2020/9/1 10:01
     **/
    protected abstract String getBeanName(Class entityClass);

    /**
     * @param applicationContext:
     * @throws
     * @Description: get spring application context
     * @return: void
     * @author Cao Xueliang
     * @date 2020/9/1 10:01
     **/
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) this.applicationContext;
        defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
    }
}
