package com.pan.spring.test.config;

import com.pan.spring.exception.BeansException;
import com.pan.spring.processor.BeanPostProcessor;
import com.pan.spring.test.bean.UserService;

public class MyBeanProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("修改location");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
