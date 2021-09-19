package com.pan.spring.factory.aware;

import com.pan.spring.context.ApplicationContext;
import com.pan.spring.exception.BeansException;

public interface ApplicationContextAware extends Aware{

    void setApplication(ApplicationContext applicationContext) throws BeansException;
}
