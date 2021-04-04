package com.myblog.pojo;

public class tag {
    private int tagid;
    private String tagname;

    @Override
    public String toString() {
        return "tag{" +
                "tagid=" + tagid +
                ", tagname='" + tagname + '\'' +
                '}';
    }

    public tag(int tagid, String tagname) {
        this.tagid = tagid;
        this.tagname = tagname;
    }

    public int getTagid() {
        return tagid;
    }

    public void setTagid(int tagid) {
        this.tagid = tagid;
    }

    public String getTagname() {
        return tagname;
    }

    public void setTagname(String tagname) {
        this.tagname = tagname;
    }
}
