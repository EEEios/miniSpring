package com.pan.spring.factory.aware;

public interface BeanNameAware extends Aware{

    void setBeanName(String name);
}
