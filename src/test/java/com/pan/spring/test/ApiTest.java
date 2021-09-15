package com.pan.spring.test;

import com.pan.spring.context.suppot.ClassPathXmlApplicationContext;
import com.pan.spring.factory.DefaultListableBeanFactory;
import com.pan.spring.io.reader.XmlBeanDefinitionReader;
import com.pan.spring.test.bean.UserService;
import com.pan.spring.test.config.MyBeanFactoryPostProcessor;
import com.pan.spring.test.config.MyBeanProcessor;
import org.junit.Test;

public class ApiTest {

    @Test
    public void testBeanFactory(){
        // 1.初始化 BeanFactory
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 3. 读取配置文件
        XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(beanFactory);
        reader.loadBeanDefinitions("classpath:springPostProcessor.xml");

        // 3. BeanDefinition 加载完成 & Bean实例化之前，修改 BeanDefinition 的属性值
        MyBeanFactoryPostProcessor beanFactoryPostProcessor = new MyBeanFactoryPostProcessor();
        beanFactoryPostProcessor.postProcessBeanFactory(beanFactory);

        // 4. Bean实例化之后，修改 Bean 属性信息
        MyBeanProcessor beanPostProcessor = new MyBeanProcessor();
        beanFactory.addBeanPostProcessor(beanPostProcessor);

        //5. 测试
        UserService userService = (UserService) beanFactory.getBean("userService");
        userService.queryUserInfo();
    }

    @Test
    public void testXmlApplication() {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:springPostProcessor.xml");

        UserService userService = applicationContext.getBean("userService", UserService.class);
        userService.queryUserInfo();
    }
}
