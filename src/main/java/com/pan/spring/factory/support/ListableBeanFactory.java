package com.pan.spring.factory.support;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.BeanFactory;

import java.util.Map;

/**
 * BeanFactory 的扩展接口
 */
public interface ListableBeanFactory extends BeanFactory {

    <T> Map<String, T> getBeanOfType(Class<T> type) throws BeansException;

    String[] getBeanDefinitionNames();
}
