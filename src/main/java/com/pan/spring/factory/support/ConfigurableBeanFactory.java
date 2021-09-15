package com.pan.spring.factory.support;

import com.pan.spring.factory.singleton.SingletonBeanRegister;

public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegister {

    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";

}