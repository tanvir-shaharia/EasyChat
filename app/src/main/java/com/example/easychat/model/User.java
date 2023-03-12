package com.example.easychat.model;

public class User {
    String name,email,phone,password,profile_pic,cover_pic,user_id;
    long usercreateAT;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile_pic() {
        return profile_pic;
    }

    public void setProfile_pic(String profile_pic) {
        this.profile_pic = profile_pic;
    }

    public String getCover_pic() {
        return cover_pic;
    }

    public void setCover_pic(String cover_pic) {
        this.cover_pic = cover_pic;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public long getUsercreateAT() {
        return usercreateAT;
    }

    public void setUsercreateAT(long usercreateAT) {
        this.usercreateAT = usercreateAT;
    }

    public User(String name, String email, String phone, String password, String profile_pic, String cover_pic, String user_id, long usercreateAT) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.profile_pic = profile_pic;
        this.cover_pic = cover_pic;
        this.user_id = user_id;
        this.usercreateAT = usercreateAT;
    }

    public User() {
    }
}

