package com.example.dechuan.model.usermanage;

import java.io.Serializable;

/**
 * @author eden
 * @description:
 * @menu
 * @date 2022/2/8 14:21
 */
public class UserData implements Serializable {
    private Object data;
    private Integer code;
    private String token;
    private Integer userKy;
    private String userName;


    public Object getData() {
        return data;
    }
    public void setData(Object data) {
        this.data = data;
    }
    public Integer getCode() {
        return code;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    public String getToken() {
        return token;
    }
    public void setToken(String token) {
        this.token = token;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserKy() {
        return userKy;
    }

    public void setUserKy(Integer userKy) {
        this.userKy = userKy;
    }
}
