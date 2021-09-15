package com.pan.spring.context;

import com.pan.spring.factory.DefaultListableBeanFactory;
import com.pan.spring.io.reader.XmlBeanDefinitionReader;

public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext{

    @Override
    protected void loadBeanDefinition(DefaultListableBeanFactory factory) {
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(factory, this);
        String[] configLocations = getConfigLocation();
        if (null != configLocations) {
            reader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocation();
}
