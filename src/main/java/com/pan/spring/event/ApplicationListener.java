package com.pan.spring.event;

import com.pan.spring.event.support.ApplicationEvent;

import java.util.EventListener;

public interface ApplicationListener<E extends ApplicationEvent> extends EventListener {

    void onApplication(E event);
}
