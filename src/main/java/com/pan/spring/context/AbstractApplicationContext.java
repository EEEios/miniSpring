package com.pan.spring.context;

import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.support.ConfigurableListableBeanFactory;
import com.pan.spring.io.loader.DefaultResourceLoader;
import com.pan.spring.processor.BeanFactoryPostProcessor;
import com.pan.spring.processor.BeanPostProcessor;

import java.util.Map;

public abstract class AbstractApplicationContext extends DefaultResourceLoader
        implements ConfigurableApplicationContext {

    @Override
    public void refresh() throws BeansException {
        // 1. 创建 BeanFactory，并根据 ResourceLoader 加载 BeanDefinition
        refreshBeanFactory();

        // 2. 获取 BeanFactory
        ConfigurableListableBeanFactory factory = getBeanFactory();

        // 3. 在 Bean 实例化前执行 BeanFactoryPostProcessor 进行定义好的自定义操作
        invokeBeanFactoryPostProcessors(factory);

        // 4. BeanPostProcessor 需要提前与其他 Bean 对象实例化前操作
        registerBeanPostProcessors(factory);

        // 5. 提前实例化单例 Bean 对象
        factory.preInstantiateSingletons();
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
}
