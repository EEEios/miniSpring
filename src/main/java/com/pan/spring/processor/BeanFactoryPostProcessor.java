package com.pan.spring.processor;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.support.ConfigurableListableBeanFactory;

public interface BeanFactoryPostProcessor {

    /**
     * 在所有 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
