package com.pan.spring.test.bean;


import com.pan.spring.context.ApplicationContext;
import com.pan.spring.exception.BeansException;
import com.pan.spring.factory.BeanFactory;
import com.pan.spring.factory.aware.ApplicationContextAware;
import com.pan.spring.factory.aware.BeanClassLoaderAware;
import com.pan.spring.factory.aware.BeanFactoryAware;
import com.pan.spring.factory.aware.BeanNameAware;

public class UserService implements BeanNameAware, BeanClassLoaderAware, ApplicationContextAware, BeanFactoryAware {

    private ApplicationContext applicationContext;
    private BeanFactory beanFactory;

    private String id;

    private String company;

    private String location;

    private IUserDao userInfo;

    @Override
    public void setApplication(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        System.out.println("ClassLoader：" + classLoader);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setBeanName(String name) {
        System.out.println("Bean Name is：" + name);
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息: " + userInfo.checkInfo(id) + "," + company + "," + location);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public IUserDao getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(IUserDao userInfo) {
        this.userInfo = userInfo;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
