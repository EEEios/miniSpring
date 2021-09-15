package com.pan.spring.test.bean;


public class UserService {

    private String id;

    private String company;

    private String location;

    private UserInfo userInfo;

    public void queryUserInfo(){
        System.out.println("查询用户信息" + userInfo.checkInfo(id) + "," + company + "," + location);
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
