package com.pan.spring.test.bean;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {

    private static Map<String, String> map = new HashMap<>();

    static {
        map.put("1", "pan");
        map.put("2", "wan");
        map.put("3", "lin");
    }

    public String checkInfo(String id){
        return map.get(id);
    }
}
