package com.myblog.pojo;

import java.time.LocalDateTime;

public class commen {
    private int commenid;
    private int toblogid;
    private int touserid;
    private int userid;
    private String commencontain;
    private LocalDateTime time;

    public commen(int commenid, int toblogid, int touserid, int userid, String commencontain, LocalDateTime time) {
        this.commenid = commenid;
        this.toblogid = toblogid;
        this.touserid = touserid;
        this.userid = userid;
        this.commencontain = commencontain;
        this.time = time;
    }

    public int getCommenid() {
        return commenid;
    }

    public void setCommenid(int commenid) {
        this.commenid = commenid;
    }

    public int getToblogid() {
        return toblogid;
    }

    public void setToblogid(int toblogid) {
        this.toblogid = toblogid;
    }

    public int getTouserid() {
        return touserid;
    }

    public void setuserid(int touserid) {
        this.touserid = touserid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getCommencontain() {
        return commencontain;
    }

    public void setCommencontain(String commencontain) {
        this.commencontain = commencontain;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
