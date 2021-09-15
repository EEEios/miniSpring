package com.pan.spring.factory;

import cn.hutool.core.util.StrUtil;
import com.pan.spring.config.BeanDefinition;
import com.pan.spring.exception.BeansException;

import java.lang.reflect.Method;

/**
 *  配置销毁方法有多种方法：1. xml配置 2. 继承接口
 *  同时，销毁方法需要注册虚拟机钩子
 *  因此创建 Adapter 进行统一的接口调用
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        if (StrUtil.isNotEmpty(destroyMethodName) &&
                !(bean instanceof DisposableBean) && "destroy".equals(this.destroyMethodName)) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName + "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
