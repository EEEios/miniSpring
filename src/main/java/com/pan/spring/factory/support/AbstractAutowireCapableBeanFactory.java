package com.pan.spring.factory.support;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.pan.spring.config.BeanDefinition;
import com.pan.spring.config.BeanReference;
import com.pan.spring.config.PropertyValue;
import com.pan.spring.config.PropertyValues;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.DisposableBean;
import com.pan.spring.factory.InitializingBean;
import com.pan.spring.factory.aware.Aware;
import com.pan.spring.factory.aware.BeanClassLoaderAware;
import com.pan.spring.factory.aware.BeanFactoryAware;
import com.pan.spring.factory.aware.BeanNameAware;
import com.pan.spring.factory.instantiation.CglibSubclassingInstantiationStrategy;
import com.pan.spring.factory.InstantiationStrategy;
import com.pan.spring.processor.BeanPostProcessor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * 该类继承 注册/获取 bean 功能
 * 并实现根据 beanDefinition 实现 bean 的创建
 *
 * spring03：实现实例化操作
 * spring04: 实现依赖注入
 * spring07: 增加初始化/销毁 Bean 的相关操作
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

            // 执行 Bean 初始化和 BeanPostProcessor 的前置/后置处理方法
            bean = initializeBean(beanName, bean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Failed to instantiate bean", e);
        }
        /**
         * 注册 Bean 的销毁方法
         */
        registerDisposableBeanIfNecessary(beanName, bean, beanDefinition);
        if (beanDefinition.isSingleton()){
            registerSingleton(beanName, bean);
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


    protected void registerDisposableBeanIfNecessary(String beanName, Object bean, BeanDefinition beanDefinition) {
        // 如果作用域不是 singleton ，则不执行销毁方法
        if (!beanDefinition.isSingleton()) return;
        // 调用销毁方法
        if (bean instanceof DisposableBean || StrUtil.isNotEmpty(beanDefinition.getDestroyMethodName())) {
            registerDisposableBean(beanName, new DisposableBeanAdapter(bean, beanName, beanDefinition));
        }
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

        // 检查 Aware 相关接口
        if (bean instanceof Aware) {
            if (bean instanceof BeanFactoryAware) {
                ((BeanFactoryAware) bean).setBeanFactory(this);
            }
            if (bean instanceof BeanClassLoaderAware) {
                ((BeanClassLoaderAware) bean).setBeanClassLoader(getClassLoader());
            }
            if (bean instanceof BeanNameAware) {
                ((BeanNameAware) bean).setBeanName(beanName);
            }
        }

        // 对文件中定义的 BeanDefinition 进行自定义操作
        Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
        try {
            invokeInitMethod(beanName, wrappedBean, beanDefinition);
        } catch (Exception e) {
            throw new BeansException("Invocation of init method of bean[" + beanName + "] failed", e);
        }
        wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
        return wrappedBean;
    }

    private void invokeInitMethod(String beanName, Object bean, BeanDefinition beanDefinition) throws Exception{
        // 1.  实现接口 InitializingBean
        if (bean instanceof InitializingBean) {
            ((InitializingBean)bean).afterPropertiesSet();
        }

        // 2. 注解配置 init-method {判断是为了避免二次执行销毁}
        String initMethodName = beanDefinition.getInitMethodName();
        if (StrUtil.isNotEmpty(initMethodName)) {
            Method initMethod = beanDefinition.getBeanClass().getMethod(initMethodName);
            if (null == initMethod) {
                throw new BeansException("[Error]Could not find an init method named '" + initMethodName + "' on bean with name '" + beanName + "'");
            }
            initMethod.invoke(bean);
        }
    }

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
        Object result = existingBean;
        for (BeanPostProcessor processor : getBeanPostProcessors()) {
            Object current = processor.postProcessAfterInitialization(result, beanName);
            if (null == current) return result;
            result = current;
        }
        return result;
    }
}
