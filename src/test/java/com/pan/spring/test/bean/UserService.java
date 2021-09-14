package com.pan.spring.test.bean;


public class UserService {

    private String id;

    private UserInfo userInfo;

    public void queryUserInfo(){
        System.out.println("查询用户信息" + userInfo.checkInfo(id));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
}
