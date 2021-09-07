package com.pan.spring;

/**
 *  存储 Bean 相关信息
 *  目前暂时使用 Object 作为存储信息
 */
public class BeanDefinition {

    private Object bean;

    public BeanDefinition(Object bean) {
        this.bean = bean;
    }

    public Object getBean() {
        return bean;
    }
}
