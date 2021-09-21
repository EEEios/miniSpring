package com.pan.spring.factory.singleton;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.DisposableBean;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 单例 beanFactory 默认实现
 * spring07: 新增 destroy 相关操作
 */
public class DefaultSingletonBeanRegistry implements SingletonBeanRegister{

    protected static final Object NULL_OBJECT = new Object();

    private Map<String, Object> singletonObjects = new HashMap<>();

    private final Map<String, DisposableBean> disposableBeanMap = new HashMap<>();

    @Override
    public Object getSingleton(String beanName) {
        return singletonObjects.get(beanName);
    }

    protected void addSingleton(String beanName, Object singletonObject) {
        singletonObjects.put(beanName, singletonObject);
    }

    public void registerDisposableBean(String beanName, DisposableBean bean) {
        disposableBeanMap.put(beanName, bean);
    }

    public void destroySingletons() {
        Set<String> ketSet = this.disposableBeanMap.keySet();
        Object[] disposalBeanNames = ketSet.toArray();

        for (int i=disposalBeanNames.length-1; i>=0; i--){
            Object beanName = disposalBeanNames[i];
            DisposableBean disposableBean = this.disposableBeanMap.remove(beanName);
            try {
                disposableBean.destroy();
            } catch (Exception e) {
                throw new BeansException("Destroy method on bean with name '" + beanName + "' threw an exception", e);
            }
        }
    }
}
