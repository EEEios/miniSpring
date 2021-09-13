package com.pan.spring.factory;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.instantiation.CglibSubclassingInstantiationStrategy;
import com.pan.spring.factory.instantiation.InstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * 该类继承 注册/获取 bean 功能
 * 并实现根据 beanDefinition 实现 bean 的创建
 *
 * 接入实例化操作
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException{
        Object bean = null;
        try{
            bean = createBeanInstance(beanDefinition, beanName, args);
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate bean", e);
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        for (Constructor c : declaredConstructors) {
            // demo 仅实现对于长度的对比，实际上需要对参数类型进行比较，否则会因相同类型的构造函数引起冲突
            if (null != args && c.getParameterTypes().length == args.length) {
                constructor = c;
                break;
            }
        }
        return getInstantiationStrategy().instance(beanDefinition, beanName, constructor, args);
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }


}
