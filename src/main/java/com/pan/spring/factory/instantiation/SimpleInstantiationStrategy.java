package com.pan.spring.factory.instantiation;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.InstantiationStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 通过反射实现的简单的实例化操作
 */
public class SimpleInstantiationStrategy implements InstantiationStrategy {

    @Override
    public Object instance(BeanDefinition beanDefinition, String beanName,
                           Constructor ctor, Object[] args) throws BeansException {
        Class clazz = beanDefinition.getBeanClass();
        try {
            if (null != clazz) {
                return clazz.getDeclaredConstructor(ctor.getParameterTypes()).newInstance(args);
            } else {
                return clazz.getDeclaredConstructor().newInstance();
            }
        } catch (InvocationTargetException | InstantiationException |
                    IllegalAccessException | NoSuchMethodException e) {
            throw new BeansException("Failed to insantiate [" + clazz.getName() + "]", e);
        }
    }
}
