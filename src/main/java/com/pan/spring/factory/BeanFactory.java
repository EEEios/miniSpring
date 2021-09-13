package com.pan.spring.factory;

import com.pan.spring.exception.BeansException;

/**
 * 定义 getBean 接口
 */
public interface BeanFactory {

    Object getBean(String name) throws BeansException;

    Object getBean(String name, Object... args) throws BeansException;
}
