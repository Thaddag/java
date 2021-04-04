package com.myblog.pojo;

import java.time.LocalDateTime;

public class chat {
    private int letterid;
    private int userid;
    private int touserid;
    private String contain;
    private LocalDateTime time;

    public chat(int letterid, int userid, int touserid, String contain, LocalDateTime time) {
        this.letterid = letterid;
        this.userid = userid;
        this.touserid = touserid;
        this.contain = contain;
        this.time = time;
    }

    public int getLetterid() {
        return letterid;
    }

    public void setLetterid(int letterid) {
        this.letterid = letterid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getTouserid() {
        return touserid;
    }

    public void setTouserid(int touserid) {
        this.touserid = touserid;
    }

    public String getContain() {
        return contain;
    }

    public void setContain(String contain) {
        this.contain = contain;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}