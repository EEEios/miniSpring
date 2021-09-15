package com.pan.spring.test;

import cn.hutool.core.io.IoUtil;
import com.pan.spring.config.BeanDefinition;
import com.pan.spring.config.BeanReference;
import com.pan.spring.config.PropertyValue;
import com.pan.spring.config.PropertyValues;
import com.pan.spring.factory.DefaultListableBeanFactory;
import com.pan.spring.io.Resource;
import com.pan.spring.io.loader.DefaultResourceLoader;
import com.pan.spring.io.reader.XmlBeanDefinitionReader;
import com.pan.spring.test.bean.UserInfo;
import com.pan.spring.test.bean.UserService;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;

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

    private DefaultResourceLoader resourceLoader;

    @Before
    public void init() {
        resourceLoader = new DefaultResourceLoader();
    }

    @Test
    public void testClassPath() throws IOException{
        Resource resource = resourceLoader.getResource("classpath:important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_file() throws IOException {
        Resource resource = resourceLoader.getResource("src/test/resources/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_url() throws IOException {
        Resource resource = resourceLoader.getResource("https://github.com/fuzhengwei/small-spring/important.properties");
        InputStream inputStream = resource.getInputStream();
        String content = IoUtil.readUtf8(inputStream);
        System.out.println(content);
    }

    @Test
    public void test_xml() {
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2. 读取配置文件&注册Bean
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:spring.xml");

        // 3. 获取Bean对象调用方法
        UserService userService = beanFactory.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }
}
