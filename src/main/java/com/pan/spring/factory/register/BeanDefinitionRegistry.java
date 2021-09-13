package com.pan.spring.factory.register;

import com.pan.spring.config.BeanDefinition;

/**
 * 注解接口
 */
public interface BeanDefinitionRegistry {

    /**
     * 注册 BeanDefinition
     *
     * @param beanName
     * @param beanDefinition
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);


}
