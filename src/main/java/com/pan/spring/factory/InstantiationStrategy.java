package com.pan.spring.factory;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;

import java.lang.reflect.Constructor;

/**
 * 实例化接口
 */
public interface InstantiationStrategy {

    Object instance(BeanDefinition beanDefinition, String beanName,
                    Constructor ctor, Object[] args) throws BeansException;
}
