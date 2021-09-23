package com.pan.spring.test;

import com.pan.spring.context.suppot.ClassPathXmlApplicationContext;
import com.pan.spring.test.event.CustomEvent;
import org.junit.Test;

public class ApiTest {

    @Test
    public void testEvent(){
        // 1. 初始化 BeanFactory
        ClassPathXmlApplicationContext applicationContext =
                new ClassPathXmlApplicationContext("classpath:spring.xml");
        applicationContext.publishedEvent(new CustomEvent(applicationContext, 1019129009086763L, "ok"));
        applicationContext.registerShutdownHook();
    }
}
