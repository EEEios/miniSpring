package com.pan.spring.context.suppot;

import com.pan.spring.context.ConfigurableApplicationContext;
import com.pan.spring.event.ApplicationEventMulticaster;
import com.pan.spring.event.ApplicationListener;
import com.pan.spring.event.support.ApplicationEvent;
import com.pan.spring.event.support.ContextClosedEvent;
import com.pan.spring.event.support.ContextRefreshedEvent;
import com.pan.spring.event.support.SimpleApplicationEventMulticaster;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.aware.ApplicationContextAwareProcessor;
import com.pan.spring.factory.support.ConfigurableListableBeanFactory;
import com.pan.spring.io.loader.DefaultResourceLoader;
import com.pan.spring.processor.BeanFactoryPostProcessor;
import com.pan.spring.processor.BeanPostProcessor;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    public static final String APPLICATION_EVENT_MULTICAST_BEAN_NAME = "applicationEventMulticaster";

    private ApplicationEventMulticaster applicationEventMulticaster;

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并根据 ResourceLoader 加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory factory = getBeanFactory();

        // 3. 添加 ApplicationContext ，令继承 ApplicationContext 的 Bean 对象都能感知所属 ApplicationContext
        factory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));

        // 4. 在 Bean 实例化前执行 BeanFactoryPostProcessor 进行定义好的自定义操作
        invokeBeanFactoryPostProcessors(factory);

        // 5. BeanPostProcessor 需要提前与其他 Bean 对象实例化前操作
        registerBeanPostProcessors(factory);

        // 6. 初始化事件发布者
        initApplicationEventMulticaster();

        // 7. 注册事件监听器
        registerListeners();

        // 8(修改前:6). 提前实例化单例 Bean 对象
        factory.preInstantiateSingletons();

        // 9. 发布容器刷新事件
        finishRefresh();
    }

    protected abstract void refreshBeanFactory() throws BeansException;

    protected abstract ConfigurableListableBeanFactory getBeanFactory();

    private void invokeBeanFactoryPostProcessors(ConfigurableListableBeanFactory factory) {
        // 此层是对 beanDefinition 的修改
        // 对 beanFactory 中的 processor 配置其所属 beanFactory （因为需要获取 factory 中的 beanDefinition）
        Map<String, BeanFactoryPostProcessor> beanFactoryPostProcessorMap = factory.getBeansOfType(BeanFactoryPostProcessor.class);
        for (BeanFactoryPostProcessor postProcessor : beanFactoryPostProcessorMap.values()) {
            postProcessor.postProcessBeanFactory(factory);
        }
    }

    private void registerBeanPostProcessors(ConfigurableListableBeanFactory factory) {
        // 此层是对目标 bean 的操作
        Map<String, BeanPostProcessor> beanPostProcessorMap = factory.getBeansOfType(BeanPostProcessor.class);
        for (BeanPostProcessor postProcessor : beanPostProcessorMap.values()) {
            factory.addBeanPostProcessor(postProcessor);
        }
    }

    // 初始化事件广播器
    private void initApplicationEventMulticaster() {
        ConfigurableListableBeanFactory beanFactory = getBeanFactory();
        applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
        beanFactory.registerSingleton(APPLICATION_EVENT_MULTICAST_BEAN_NAME, applicationEventMulticaster);
    }

    private void registerListeners() {
        Collection<ApplicationListener> applicationListeners = getBeansOfType(ApplicationListener.class).values();
        for (ApplicationListener listener : applicationListeners) {
            applicationEventMulticaster.addApplicationListener(listener);
        }
    }

    private void finishRefresh() {
        publishedEvent(new ContextRefreshedEvent(this));
    }

    @Override
    public void publishedEvent(ApplicationEvent event) {
        applicationEventMulticaster.multicastEvent(event);
    }

    @Override
    public Object getBean(String name) throws BeansException {
        return getBeanFactory().getBean(name);
    }

    @Override
    public Object getBean(String name, Object... args) throws BeansException {
        return getBeanFactory().getBean(name, args);
    }

    @Override
    public <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return getBeanFactory().getBean(name, requiredType);
    }

    @Override
    public <T> Map<String, T> getBeansOfType(Class<T> type) throws BeansException {
        return getBeanFactory().getBeansOfType(type);
    }

    @Override
    public String[] getBeanDefinitionNames() {
        return getBeanFactory().getBeanDefinitionNames();
    }

    @Override
    public void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(this::close));
    }

    @Override
    public void close() {

        // 发布容器关闭事件
        publishedEvent(new ContextClosedEvent(this));

        getBeanFactory().destroySingletons();
    }
}
