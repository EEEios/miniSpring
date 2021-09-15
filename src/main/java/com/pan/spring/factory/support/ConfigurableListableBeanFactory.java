package com.pan.spring.factory.support;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;

public interface ConfigurableListableBeanFactory extends ListableBeanFactory, AutowireCapableBeanFactory, ConfigurableBeanFactory {

    BeanDefinition getBeanDefinition(String beanName) throws BeansException;

}
