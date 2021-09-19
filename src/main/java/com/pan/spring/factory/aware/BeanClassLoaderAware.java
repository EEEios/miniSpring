package com.pan.spring.factory.aware;

public interface BeanClassLoaderAware extends Aware{

    void setBeanClassLoader(ClassLoader classLoader);
}
