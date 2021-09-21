package com.pan.spring.factory.support;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.FactoryBean;
import com.pan.spring.factory.singleton.DefaultSingletonBeanRegistry;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class FactoryBeanRegistrySupport extends DefaultSingletonBeanRegistry {

    private final Map<String, Object> factoryBeanObjectCache = new ConcurrentHashMap<>();

    // 在Factory的缓存中查询
    protected Object getCacheObjectForFactoryBean(String beanName){
        Object o = this.factoryBeanObjectCache.get(beanName);
        return (o != NULL_OBJECT ? o : null);
    }

    protected Object getObjectFromFactoryBean(FactoryBean factoryBean, String beanName){
        if (factoryBean.isSingleton()) {
            Object o = this.factoryBeanObjectCache.get(beanName);
            if (o == null){
                o = doGetObjectFromFactoryBean(factoryBean, beanName);
                this.factoryBeanObjectCache.put(beanName, (o != null? o:NULL_OBJECT));
            }
            return (o != NULL_OBJECT ? o : null);
        } else {
            return doGetObjectFromFactoryBean(factoryBean, beanName);
        }
    }

    private Object doGetObjectFromFactoryBean(final FactoryBean factoryBean, final String beanName) {
        try {
            return factoryBean.getObject();
        } catch (Exception e){
            throw new BeansException("FactoryBean threw exception on object [" + beanName + "] creation", e);
        }
    }
}
