## ApplicationContext

1. refreshBeanFactory：创建工厂

    - `new DefaultListableBeanFactory()`
    - `reader.loadBeanDefinitions(configLocations);`
    
    
    
2. getBeanFactory：获取工厂

    - `getBeanFactory()`
    
    

3. `beanFactory.addBeanPostProcessor(new ApplicationContextAwareProcessor(this));`：设置 ApplicationContext 感知

    > 由于 Application 不能在创建 Bean 时直接获得，因此需要包装到一个 PostProcessor 中

    

4. invokeBeanFactoryPostProcessors：实例化前执行 BeanFactoryPostProcessor

    - `factory.getBeansOfType(BeanFactoryPostProcessor.class)`
    - 执行 `postProcessor.postProcessBeanFactory(factory);`

    > BeanFactory 主要对 BeanDefinitino 等非 Bean 实例信息进行修改

    

5. registerBeanPostProcessor：实例化 BeanPostProcessor 以在进行 Bean 的初始化时调用

    - `factory.getBeansOfType(BeanPostProcessor.class)`

    - `factory.addBeanPostProcessor(postProcessor);`

      

6. preInstantiateSingletons：实例化 Bean 对象

    进行作用域判断：
    
    - 若单例则尝试直接获取： `getSingleton()`，若为单例会注册在容器中
    
    - 获取失败需要进行创建：`createBean(name, beanDefinition, args)`
    
      > 给 Bean 填充属性：`applyPropertyValues(beanName, bean, beanDefinition);`
      >
      > 执行 Bean 的初始化方法和 BeanPostProcessor 的前置和后置处理方法：`initializeBean(beanName, bean, beanDefinition);`
      >
      > ```
      > // initializeBean(beanName, bean, beanDefinition)
      > 
      > // 1. 执行 BeanPostProcessor Before 处理
      > Object wrappedBean = applyBeanPostProcessorsBeforeInitialization(bean, beanName);
      > 
      > // 2. 执行初始化方法
      > invokeInitMethods(beanName, wrappedBean, beanDefinition);
      > 
      > // 2. 执行 BeanPostProcessor After 处理
      > wrappedBean = applyBeanPostProcessorsAfterInitialization(bean, beanName);
      > ```
    
    统一通过 `(T) getObjectForBeanInstance(bean, name)` 进行返回。
    
    在该方法中会判断是否为 FactoryBean ，若是则会查询缓存（当 FactoryBean 为 Singleton 时该 FactoryBean 会被加入到缓存中），否则会直接返回。
    
    FactotyBean 会进行查询缓存操作（该缓存为被维护的一个 Map<String, Object> 属性），若不存在缓存则会对该 FactoryBean 进行记录并调用其 `getObject()` 方法构造代理对象。
