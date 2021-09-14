package com.pan.spring.factory;

import cn.hutool.core.bean.BeanException;
import cn.hutool.core.bean.BeanUtil;
import com.pan.spring.config.BeanDefinition;
import com.pan.spring.config.BeanReference;
import com.pan.spring.config.PropertyValue;
import com.pan.spring.config.PropertyValues;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.instantiation.CglibSubclassingInstantiationStrategy;
import com.pan.spring.factory.instantiation.InstantiationStrategy;

import java.lang.reflect.Constructor;

/**
 * 该类继承 注册/获取 bean 功能
 * 并实现根据 beanDefinition 实现 bean 的创建
 *
 * spring03：实现实例化操作
 * spring04: 实现依赖注入
 */
public abstract class AbstractAutowireCapableBeanFactory extends AbstractBeanFactory{

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


}
