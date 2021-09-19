ApplicationContext

1. refreshBeanFactory：创建工厂

- ```
  new DefaultListableBeanFactory()
  ```

- ```
  reader.loadBeanDefinitions(configLocations);
  ```

2. getBeanFactory：获取工厂

- ```
  getBeanFactory()
  ```

3. `beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));`：设置 ApplicationContext 感知

   > 由于 Application 不能在创建 Bean 时直接获得，因此需要包装到一个 PostProcessor 中

4. invokeBeanFactoryPostProcessors：实例化前执行 BeanFactoryPostProcessor

- ```
  factory.getBeansOfType(BeanFactoryPostProcessor.class)
  ```

- 执行 `postProcessor.postProcessBeanFactory(factory);`

4. registerBeanPostProcessor：实例化 BeanPostProcessor

- ```
  factory.getBeansOfType(BeanPostProcessor.class)
  ```

- ```
  factory.addBeanPostProcessor(postProcessor);
  ```

5. preInstantiateSingletons：实例化 Bean 对象

- 调用到 `createBean(String beanName, BeanDefinition beanDefinition, Object[] args)()`

  - 通过构造方法创建 Bean ：`createBeanInstance(beanDefinition, beanName, args)`

  - 给 Bean 填充属性：`applyPropertyValues(beanName, bean, beanDefinition);`

  - 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法：`initializeBean(beanName, bean, beanDefinition);`

    ```
    // initializeBean(beanName, bean, beanDefinition)
    
    // 1. 执行 BeanPostProcessor Before 处理
    Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
    
    // 2. 执行初始化方法
    invokeInitMethods(beanName, wrappedBean, beanDefinition);
    
    // 2. 执行 BeanPostProcessor After 处理
    wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
    ```



