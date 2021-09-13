package com.pan.spring.factory.singleton;

/**
 * 单例模式 Bean 相关的接口
 */
public interface SingletonBeanRegister {

    Object getSingleton(String beanName);

}
