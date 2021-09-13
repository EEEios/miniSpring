package com.pan.spring.factory.singleton;

import java.util.HashMap;
import java.util.Map;

/**
 * 单例 beanFactory 默认实现
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegister{

    private Map<String, Object> singletonObjects = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }
}
