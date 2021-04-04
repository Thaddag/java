package com.myblog.pojo;

import java.time.LocalDateTime;

public class emailcheck {
    private String email;
    private String checkcode;
    private LocalDateTime createtime;

    public emailcheck(String email, String checkcode, LocalDateTime createtime) {
        this.email = email;
        this.checkcode = checkcode;
        this.createtime = createtime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCheckcode() {
        return checkcode;
    }

    public void setCheckcode(String checkcode) {
        this.checkcode = checkcode;
    }

    public LocalDateTime getCreatetime() {
        return createtime;
    }

    public void setCreatetime(LocalDateTime createtime) {
        this.createtime = createtime;
    }
}
