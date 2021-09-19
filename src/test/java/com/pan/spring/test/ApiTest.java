package com.pan.spring.test;

import com.pan.spring.context.suppot.ClassPathXmlApplicationContext;
import com.pan.spring.test.bean.UserInfo;
import com.pan.spring.test.bean.UserService;
import org.junit.Test;

public class ApiTest {

    @Test
    public void testBeanFactory(){
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取 Bean 对象调用方法
        UserInfo userInfo = applicationContext.getBean("userInfo", UserInfo.class);
        System.out.println(userInfo.checkInfo("1"));
        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void testHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(()->System.out.println("close!")));
    }
}
