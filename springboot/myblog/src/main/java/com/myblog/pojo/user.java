package com.myblog.pojo;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class user {
    private int id;
    private String username;
    private String userpassword;
    private String email;
    private int img;
    private int level;
    private LocalDateTime time;
    private int enable;

    public int getEnable() {
        return enable;
    }

    public void setEnable(int enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "user{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", userpassword='" + userpassword + '\'' +
                ", email='" + email + '\'' +
                ", img=" + img +
                ", level=" + level +
                ", time=" + time +
                '}';
    }

    public user(int id, String username, String userpassword, String email, int img, int level, LocalDateTime time,int enable) {
        this.id = id;
        this.username = username;
        this.userpassword = userpassword;
        this.email = email;
        this.img = img;
        this.level = level;
        this.time = time;
        this.enable = enable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
