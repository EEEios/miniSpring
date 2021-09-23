package com.pan.spring.context;

import com.pan.spring.event.ApplicationEventPublisher;
import com.pan.spring.factory.support.HierarchicalBeanFactory;
import com.pan.spring.factory.support.ListableBeanFactory;
import com.pan.spring.io.ResourceLoader;

public interface ApplicationContext extends ListableBeanFactory, HierarchicalBeanFactory,
        ResourceLoader, ApplicationEventPublisher {
}