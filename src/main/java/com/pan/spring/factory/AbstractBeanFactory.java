package com.pan.spring.factory;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.singleton.DefaultSingletonBeanRegistry;

/**
 * 接入了 DefaultSingletonRegistry 和 BeanFactory
 * 目前该类能够根据 BeanFactory 接口标准提供单例 Bean 的获取方法
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements BeanFactory {

    @Override
    public Object getBean(String name) throws BeansException {
        Object bean = getSingleton(name);
        if (bean != null){
            return bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return createBean(name, beanDefinition);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition) throws BeansException;
}
