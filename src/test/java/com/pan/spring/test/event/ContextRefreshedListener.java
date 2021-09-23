package com.pan.spring.test.event;

import com.pan.spring.event.ApplicationListener;
import com.pan.spring.event.support.ContextRefreshedEvent;

public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplication(ContextRefreshedEvent event) {
        System.out.println("刷新事件：" + this.getClass().getName());
    }
}
