package com.pan.spring.factory;

import com.pan.spring.exception.BeansException;

/**
 * 用于注册/获取 Bean
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;
}
