package com.pan.spring.factory.aware;

import com.pan.spring.factory.BeanFactory;

public interface BeanFactoryAware extends Aware{

    void setBeanFactory(BeanFactory beanFactory);
}
