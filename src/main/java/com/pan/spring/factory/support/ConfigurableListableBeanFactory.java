package com.pan.spring.factory.support;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;
import com.pan.spring.processor.BeanPostProcessor;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    void preInstantiateSingletons() throws BeansException;

    void addBeanPostProcessor(BeanPostProcessor beanPostProcessor);
}
