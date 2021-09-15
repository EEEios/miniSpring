package com.pan.spring.context.suppot;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.DefaultListableBeanFactory;
import com.pan.spring.factory.support.ConfigurableListableBeanFactory;

public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext{

    private DefaultListableBeanFactory factory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory factory = createBeanFactory();
        loadBeanDefinition(factory);
        this.factory = factory;
    }

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return factory;
    }

    private  DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinition(DefaultListableBeanFactory factory);


}
