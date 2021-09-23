package com.pan.spring.event.support;

import com.pan.spring.event.ApplicationListener;
import com.pan.spring.factory.BeanFactory;

public class SimpleApplicationEventMulticaster extends AbstractApplicationMulticaster{

    public SimpleApplicationEventMulticaster(BeanFactory beanFactory) {
        setBeanFactory(beanFactory);
    }

    @Override
    public void multicastEvent(ApplicationEvent event) {
        for (final ApplicationListener listener : getApplicationListener(event)) {
            listener.onApplication(event);
        }
    }
}
