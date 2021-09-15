package com.pan.spring.factory;

import cn.hutool.core.bean.BeanUtil;
import com.pan.spring.config.BeanDefinition;
import com.pan.spring.config.BeanReference;
import com.pan.spring.config.PropertyValue;
import com.pan.spring.config.PropertyValues;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.instantiation.CglibSubclassingInstantiationStrategy;
import com.pan.spring.factory.instantiation.InstantiationStrategy;
import com.pan.spring.factory.support.AutowireCapableBeanFactory;
import com.pan.spring.processor.BeanPostProcessor;

import java.lang.reflect.Constructor;

/**
 * 该类继承 注册/获取 bean 功能
 * 并实现根据 beanDefinition 实现 bean 的创建
 *
 * spring03：实现实例化操作
 * spring04: 实现依赖注入
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory
        implements AutowireCapableBeanFactory {

    private InstantiationStrategy instantiationStrategy = new CglibSubclassingInstantiationStrategy();

    @Override
    protected Object createBean(String beanName, BeanDefinition beanDefinition, Object[] args) throws BeansException{
        Object bean = null;
        try{
            // 创建 Bean
            bean = createBeanInstance(beanDefinition, beanName, args);
            // 注入属性
            applyPropertyValues(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate bean", e);
        }
        return bean;
    }

    protected Object createBeanInstance(BeanDefinition beanDefinition, String beanName, Object[] args) {
        Constructor constructor = null;
        Class<?> beanClass = beanDefinition.getBeanClass();
        Constructor<?>[] declaredConstructors = beanClass.getDeclaredConstructors();

        for (Constructor c : declaredConstructors) {
            // demo 仅实现对于长度的对比，实际上需要对参数类型进行比较，否则会因相同类型的构造函数引起冲突
            if (null != args && c.getParameterTypes().length == args.length) {
                constructor = c;
                break;
            }
        }
        return getInstantiationStrategy().instance(beanDefinition, beanName, constructor, args);
    }

    /**
     * Bean 的属性填充
     */
    protected void applyPropertyValues(String beanName, Object bean, BeanDefinition beanDefinition) {
        try {
            PropertyValues propertyValues = beanDefinition.getPropertyValues();
            for (PropertyValue pv : propertyValues.getPropertyValues()) {
                String name = pv.getName();
                Object value = pv.getValue();

                if (value instanceof BeanReference){
                    BeanReference beanReference = (BeanReference) value;
                    value = getBean(beanReference.getBeanName());
                }
                BeanUtil.setFieldValue(bean, name, value);
            }
        } catch (Exception e){
            throw new BeansException("Failed to set property values: " + beanName);
        }
    }

    public InstantiationStrategy getInstantiationStrategy() {
        return instantiationStrategy;
    }

    public void setInstantiationStrategy(InstantiationStrategy instantiationStrategy) {
        this.instantiationStrategy = instantiationStrategy;
    }

    /**
     * 上下文相关操作
     * @param beanName
     * @param bean
     * @param beanDefinition
     * @return
     */
    public Object initializeBean(String beanName, Object bean, BeanDefinition beanDefinition) {

        // 对文件中定义的 BeanDefinition 进行自定义操作
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        invokeInitMethod(beanName, wrappedBean, beanDefinition);
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethod(String beanName, Object wrappedBean, BeanDefinition beanDefinition) {}

    @Override
    public Object applyBeanPostProcessorsBeforeInitialization(Object existingBean, String beanName) throws BeansException {
        Object result = existingBean;
        for (BeanPostProcessor postProcessor : getBeanPostProcessors()){
            Object current = postProcessor.postProcessBeforeInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }

    @Override
    public Object applyBeanPostProcessorsAfterInitialization(Object existingBean, String beanName) throws BeansException {
        return null;
    }
}
