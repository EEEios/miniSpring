package com.pan.spring.event.support;

import com.pan.spring.event.ApplicationEventMulticaster;
import com.pan.spring.event.ApplicationListener;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.BeanFactory;
import com.pan.spring.factory.aware.BeanFactoryAware;
import com.pan.spring.util.ClassUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class AbstractApplicationMulticaster implements ApplicationEventMulticaster, BeanFactoryAware {

    public final Set<ApplicationListener<ApplicationEvent>> applicationListeners = new LinkedHashSet<>();

    private BeanFactory beanFactory;

    @Override
    public void addApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.add((ApplicationListener<ApplicationEvent>) listener);
    }

    @Override
    public void removeApplicationListener(ApplicationListener<?> listener) {
        applicationListeners.remove(listener);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    // 查看监听器是否对事件感兴趣
    protected Collection<ApplicationListener> getApplicationListener(ApplicationEvent event) {
        LinkedList<ApplicationListener> allListeners = new LinkedList<>();
        for (ApplicationListener<ApplicationEvent> listener : applicationListeners) {
            if (supportEvent(listener, event)) allListeners.add(listener);
        }
        return allListeners;
    }

    //TODO 分析逻辑
    protected boolean supportEvent(ApplicationListener<ApplicationEvent> applicationListener, ApplicationEvent event) {
        Class<? extends ApplicationListener> listenerClass = applicationListener.getClass();

        // 判断 CglibSubclassingInstantiationStrategy、SimpleInstantiationStrategy 不同的实例化类型，需要判断后获取目标 class
        Class<?> targetClass = ClassUtils.isCglibProxyClass(listenerClass) ? listenerClass.getSuperclass() : listenerClass;

        Type genericInterface = targetClass.getGenericInterfaces()[0];
        Type actualTypeArgument = ((ParameterizedType) genericInterface).getActualTypeArguments()[0];
        String className = actualTypeArgument.getTypeName();

        Class<?> eventClassName;
        try {
            eventClassName = Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new BeansException("[Error] wrong class name:" + className);
        }

        // 判断从 listener 中获取的 eventClassName 与参数 event.class() 是否相同，或是否是其超类或超接口
        // A.isAssignableFrom(B) ，判断 A 是否为 B 的父类
        return eventClassName.isAssignableFrom(event.getClass());
    }
}
