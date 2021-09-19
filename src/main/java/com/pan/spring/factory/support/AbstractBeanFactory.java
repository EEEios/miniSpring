package com.pan.spring.factory.support;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.singleton.DefaultSingletonBeanRegistry;
import com.pan.spring.factory.support.ConfigurableBeanFactory;
import com.pan.spring.processor.BeanPostProcessor;
import com.pan.spring.util.ClassUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 接入了 DefaultSingletonRegistry 和 BeanFactory
 * 目前该类能够根据 BeanFactory 接口标准提供单例 Bean 的获取方法
 * - Spring06:接入上下文操作 beanPostProcessor
 */
public abstract class AbstractBeanFactory extends DefaultSingletonBeanRegistry implements ConfigurableBeanFactory {

    private ClassLoader classLoader = ClassUtils.getDefaultClassLoader();

    private final List<BeanPostProcessor> beanPostProcessors = new ArrayList<>();

    @Override
    public Object getBean(String name) throws BeansException {
        return doGetBean(name ,null);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return doGetBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return (T) getBean(name);
    }

    /**
     * getBean() 方法动作
     */
    protected <T>T doGetBean(final String name, final Object[] args) {
        Object bean = getSingleton(name);
        if (bean != null){
            return (T)bean;
        }

        BeanDefinition beanDefinition = getBeanDefinition(name);
        return (T) createBean(name, beanDefinition, args);
    }

    protected abstract BeanDefinition getBeanDefinition(String beanName) throws BeansException;

    protected abstract Object createBean(String beanName, BeanDefinition beanDefinition, Object args[]) throws BeansException;

    @Override
    public void addBeanPostProcessor(BeanPostProcessor beanPostProcessor) {
        this.beanPostProcessors.remove(beanPostProcessor);
        this.beanPostProcessors.add(beanPostProcessor);
    }

    public List<BeanPostProcessor> getBeanPostProcessors() {
        return beanPostProcessors;
    }

    public ClassLoader getClassLoader(){return classLoader;}
}
