package com.pan.spring.event;

import com.pan.spring.event.support.ApplicationEvent;

public interface ApplicationEventPublisher {

    void publishedEvent(ApplicationEvent event);
}
