package com.pan.spring.io.reader;

import com.pan.spring.factory.BeanDefinitionRegistry;
import com.pan.spring.io.BeanDefinitionReader;
import com.pan.spring.io.ResourceLoader;
import com.pan.spring.io.loader.DefaultResourceLoader;

/**
 * 此层实现 Reader 和 Registry 绑定
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry() {
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }

}