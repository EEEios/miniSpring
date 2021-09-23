package com.pan.spring.event.support;

import com.pan.spring.context.ApplicationContext;

public class ApplicationContextEvent extends ApplicationEvent {

    public ApplicationContextEvent(Object source) {
        super(source);
    }

    public final ApplicationContext getApplication() {
        return (ApplicationContext) getSource();
    }
}
