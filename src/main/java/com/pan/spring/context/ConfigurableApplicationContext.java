package com.pan.spring.context;

import com.pan.spring.exception.BeansException;

/**
 * spring07: 增加虚拟机关闭钩子注册调用销毁方法
 */
public interface ConfigurableApplicationContext extends ApplicationContext {

    /**
     * 刷新容器
     */
    void refresh() throws BeansException;

    void registerShutdownHook();

    void close();

}
