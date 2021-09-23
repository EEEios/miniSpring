package com.pan.spring.event;

import com.pan.spring.event.support.ApplicationEvent;

/**
 * 事件广播器
 */
public interface ApplicationEventMulticaster {

    void addApplicationListener(ApplicationListener<?> listener);

    void removeApplicationListener(ApplicationListener<?> listener);

    // 广播事件
    void multicastEvent(ApplicationEvent event);
}
