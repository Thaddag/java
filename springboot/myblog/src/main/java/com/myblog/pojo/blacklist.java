package com.myblog.pojo;

public class blacklist {
    private int userid;
    private int touserid;

    public blacklist(int userid, int touserid) {
        this.userid = userid;
        this.touserid = touserid;
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
}
