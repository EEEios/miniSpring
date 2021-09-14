package com.pan.spring.test;

import com.pan.spring.config.BeanDefinition;
import com.pan.spring.config.BeanReference;
import com.pan.spring.config.PropertyValue;
import com.pan.spring.config.PropertyValues;
import com.pan.spring.factory.DefaultListableBeanFactory;
import com.pan.spring.test.bean.UserInfo;
import com.pan.spring.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void testBeanFactory(){
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2.注册 beanDefinition （还未实现从配置文件中读入bean）
//        BeanDefinition beanDefinition = new BeanDefinition(UserInfo.class);
        beanFactory.registerBeanDefinition("userInfo", new BeanDefinition(UserInfo.class));

        // 3. UserService 设置属性[uId、userDao]
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("id", "2"));
        propertyValues.addPropertyValue(new PropertyValue("userInfo",new BeanReference("userInfo")));

        // 4. 注册 UserService
        BeanDefinition userBeanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", userBeanDefinition);

        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }
}
