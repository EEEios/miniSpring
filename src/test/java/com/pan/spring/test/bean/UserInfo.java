package com.pan.spring.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    private Map<String, String> map;

    public void initDataMethod() {
        System.out.println("执行：init-method");
        map = new HashMap<>();
        map.put("1", "pan");
        map.put("2", "wan");
        map.put("3", "lin");
    }

    public void destroyDataMethod(){
        System.out.println("执行：destroy-method");
        map.clear();
    }

    public String checkInfo(String id){
        return map.get(id);
    }
}
