package com.atguigu.rabbit.bean;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String email;

    public User(){

    }
    public User(String username,String email){
        this.username = username;
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getUsername() {
        return username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
