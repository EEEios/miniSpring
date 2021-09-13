package com.pan.spring.factory;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;

/**
 * 该类继承 注册/获取 bean 功能
 * 并实现根据 beanDefinition 实现 bean 的创建
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition) {
        Object bean = null;
        try{
            //模拟 bean 的实例化过程
            bean = beanDefinition.getBeanClass().newInstance();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
