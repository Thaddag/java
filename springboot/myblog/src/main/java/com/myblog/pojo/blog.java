package com.myblog.pojo;

import java.time.LocalDateTime;

public class blog {
    private int blogid;
    private int userid;
    private String title;
    private String contain;
    private LocalDateTime time;
    private int top;

    public blog(int blogid, int userid, String title, String contain, LocalDateTime time, int top) {
        this.blogid = blogid;
        this.userid = userid;
        this.title = title;
        this.contain = contain;
        this.time = time;
        this.top = top;
    }

    public int getBlogid() {
        return blogid;
    }

    public void setBlogid(int blogid) {
        this.blogid = blogid;
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }
}
