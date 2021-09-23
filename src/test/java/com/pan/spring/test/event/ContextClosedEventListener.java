package com.pan.spring.test.event;

import com.pan.spring.event.ApplicationListener;
import com.pan.spring.event.support.ContextClosedEvent;

public class ContextClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplication(ContextClosedEvent event) {
        System.out.println("关闭事件" + this.getClass().getName());
    }
}
