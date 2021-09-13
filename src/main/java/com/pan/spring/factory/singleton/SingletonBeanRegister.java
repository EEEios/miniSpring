package com.pan.spring.factory.singleton;

/**
 * 单例模式 getBean 接口
 */
public interface SingletonBeanRegister {

    Object getSingleton(String beanName);

}
