package com.myblog.pojo;

public class userphoto {
    private int userid;
    private int imgid;

    public userphoto(int userid, int imgid) {
        this.userid = userid;
        this.imgid = imgid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getImgid() {
        return imgid;
    }

    public void setImgid(int imgid) {
        this.imgid = imgid;
    }
}
