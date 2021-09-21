package com.pan.spring.test;

import com.pan.spring.context.suppot.ClassPathXmlApplicationContext;
import com.pan.spring.test.bean.IUserDao;
import com.pan.spring.test.bean.UserInfo;
import com.pan.spring.test.bean.UserService;
import org.junit.Test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;

public class ApiTest {

    @Test
    public void testBeanFactory(){
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        // 2. 获取 Bean 对象调用方法
        UserService userService01 = applicationContext.getBean("userService", UserService.class);
        UserService userService02 = applicationContext.getBean("userService", UserService.class);

        System.out.println(userService01);
        System.out.println(userService02);

        System.out.println(userService01 + "hex: " + Integer.toHexString(userService01.hashCode()));
        System.out.println(userService02 + "hex: " + Integer.toHexString(userService02.hashCode()));
    }

    @Test
    public void testFactoryBean() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.registerShutdownHook();

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }

    @Test
    public void testReflect() {
        InvocationHandler handler = (proxy, method, args) -> {
            Map<String, String> map = new HashMap<>();
            map.put("1", "001");
            map.put("2", "002");
            map.put("3", "003");
            return "已被代理" + method.getName() + ": " + map.get(args[0].toString());
        };
        // 虽然方法抛出 NullPointerException ，但是实体对象已经创建完成
        IUserDao iUserDao = (IUserDao) Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new  Class[]{IUserDao.class}, handler);
        UserService userService = new UserService();
        userService.setUserInfo(iUserDao);
        userService.setId("1");
        userService.setLocation("s");
        userService.setCompany("asd");
        userService.queryUserInfo();
    }
}
