package com.pan.spring.test.config;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.config.PropertyValue;
import com.pan.spring.config.PropertyValues;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.support.ConfigurableListableBeanFactory;
import com.pan.spring.processor.BeanFactoryPostProcessor;

public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

        BeanDefinition beanDefinition = factory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();

        propertyValues.addPropertyValue(new PropertyValue("company", "修改"));
    }
}
