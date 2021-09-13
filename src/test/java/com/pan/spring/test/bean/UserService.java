package com.pan.spring.test.bean;


public class UserService {

    private String name;

    public UserService() {
        System.out.println("[UserService] 调用默认无参构造");
    }

    public UserService(String name) {
        System.out.println("[UserService] 调用构造：[String name]");
        this.name = name;
    }

    public void queryUserInfo(){
        System.out.println("查询用户信息");
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder("");
        sb.append(" ").append(name);
        return sb.toString();
    }
}
