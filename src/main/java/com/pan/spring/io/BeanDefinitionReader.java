package com.pan.spring.io;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.BeanDefinitionRegistry;

/**
 * 使用 Reader 首先需要绑定 Registry，使之能够读取 bean 后进行注册
 */
public interface BeanDefinitionReader {

    BeanDefinitionRegistry getRegistry();

    ResourceLoader getResourceLoader();

    void loadBeanDefinitions(Resource resource) throws BeansException;

    void loadBeanDefinitions(Resource... resources) throws BeansException;

    void loadBeanDefinitions(String location) throws BeansException;

    void loadBeanDefinitions(String... locations) throws BeansException;

}