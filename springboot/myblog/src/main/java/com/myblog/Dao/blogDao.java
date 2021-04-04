package com.myblog.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.myblog.pojo.blog;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import com.myblog.pojo.tag;

@Repository
public class blogDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public blog getblogbyid(int blogid){
        String sql = "select * from blog where blogid = ?";
        Map<String,Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql,blogid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        blog blog =new blog((int)map.get("blogid"),(int) map.get("userid"), (String)map.get("title"), (String) map.get("contain"), (LocalDateTime) map.get("time"),(int)map.get("top"));
        return blog;
    }
    public void addblog(int userid, String title, String contain,int top,List<Integer> tagslist){
        String sql = "insert into blog(userid,title,contain,time,top) value (?,?,?,?,?)";
        Object[] objects =new Object[5];
        objects[0] = userid;
        objects[1] = title;
        objects[2] = contain;
        objects[3] = new Date();
        objects[4] = top;
        jdbcTemplate.update(sql,objects);
        int blogid = getmaxblogid();
        addblogtags(blogid,tagslist);
    }
    public int getmaxblogid(){
        String sql ="select MAX(blogid) from blog";
        Map<String,Object> blogidmap = jdbcTemplate.queryForMap(sql);
        return (int)blogidmap.get("MAX(blogid)");
    }
    public List<tag> searchtagsbyblog(int blogid){
        String sql ="select * from blog_tag where blogid = ?";
        List<Map<String,Object>> list_map = null;
        try {
            list_map = jdbcTemplate.queryForList(sql,blogid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        List<tag> tagList = new ArrayList<tag>();
        Map<String,Object> map =null;
        for(int i=0;i<list_map.size();i++ ){
            map = list_map.get(i);
            int tagid = (int) map.get("tagid");
            tag tag = gettagbyid(tagid);
            tagList.add(tag);
        }
        return tagList;
    }

    public void addblogtags(int blogid,List<Integer> taglist){
        delblogtags(blogid);
        Object[] objects =new Object[2];
        objects[0] = blogid;
        for(int i=0;i<taglist.size();i++){
            int tagid = taglist.get(i);
            String sql = "insert into blog_tag(blogid,tagid) value(?,?)";
            objects[1]=tagid;
            jdbcTemplate.update(sql,objects);
        }

    }
    public void delblogtags(int blogid){
        String sql = "delete from blog_tag where blogid =?";
        jdbcTemplate.update(sql,blogid);
    }
    public tag gettagbyid(int tagid){
        String sql = "select * from tag where tagid = ?";
        Map<String,Object> map =null;
        try {
            map = jdbcTemplate.queryForMap(sql,tagid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        tag tag =new tag((int)map.get("tagid"),(String) map.get("tagname"));
        return tag;
    }
    public List<blog> getuserblogsbyuserid(int userid){
        String sql ="select * from blog where userid = ? order by top desc";
        List<Map<String,Object>> mapList =new ArrayList<Map<String,Object>>();
        try {
            mapList = jdbcTemplate.queryForList(sql,userid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        List<blog> blogList = new ArrayList<blog>();
        for(int i=0;i<mapList.size();i++){
            blog blog = new blog((int)mapList.get(i).get("blogid")
                    ,(int) mapList.get(i).get("userid")
                    , (String)mapList.get(i).get("title")
                    , (String) mapList.get(i).get("contain")
                    , (LocalDateTime) mapList.get(i).get("time")
                    ,(int)mapList.get(i).get("top"));
            blogList.add(blog);
        }
        return blogList;

    }
    public int getblogcountbyid(int userid){
        String sql ="select * from blog where userid = ?";
        List<Map<String,Object>> maplist =null;
        try {
            maplist = jdbcTemplate.queryForList(sql,userid);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        return maplist.size();
    }
    public boolean delblog(int blogid,int userid){
        String sql = "delete from blog where blogid = ? and userid = ?";
        Object[] objects = new Object[2];
        objects[0] = blogid;
        objects[1] = userid;
        if(jdbcTemplate.update(sql, objects) == 1)return true;
        else return false;
        //System.out.println("jdbc blog :" + jdbcTemplate.update(sql, objects));
    }
    public blog getblogbyid(int blogid,int userid){
        String sql = "select * from blog where blogid = ? and userid = ?";
        Object[] objects = new Object[2];
        objects[0] = blogid;
        objects[1] = userid;
        Map<String,Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql,objects);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        blog blog =new blog((int)map.get("blogid"),(int) map.get("userid"), (String)map.get("title"), (String) map.get("contain"), (LocalDateTime) map.get("time"),(int)map.get("top"));
        return blog;
    }
    public void reeditblog(int blogid,String title, String contain){
        String sql = "update blog set title = ?,contain = ? where blogid = ?";
        Object[] objects =new Object[3];
        objects[0] = title;
        objects[1] = contain;
        objects[2] = blogid;
        jdbcTemplate.update(sql,objects);
        //int blogid = getmaxblogid();
        //addblogtags(blogid,tagslist);
    }
    public void reeditblogtags(int blogid, List<Integer>tagslist){
        delblogtags(blogid);
        addblogtags(blogid,tagslist);
    }
    public void settopstat(int blogid,int top){
        String sql ="update blog set top = ? where blogid = ?";
        Object[] objects = new Object[2];
        objects[0] = top;
        objects[1] = blogid;
        jdbcTemplate.update(sql,objects);
    }
    public List<blog> getallblogs(){
        String sql = "select * from blog";
        List<blog> blogList = blogsbysql(sql);
        return blogList;
    }
    public List<blog> getblogsbytag(int tagid){
        String sql = "select * from blog_tag where tagid = "+tagid;
        List<blog> blogList = blogsbysql(sql);
        return blogList;
    }
    public List<blog> searchtitlelike(String like){
        String sql  = "select * from blog where title like '%"+like+"%'";
        List<blog> blogList = blogsbysql(sql);
        System.out.println("search result size:" + blogList.size());
        return blogList;
    }
    public void deltagsbyblog(int blogid){
        String sql = "delete from blog_tag where blogid =?";
        jdbcTemplate.update(sql,blogid);
    }
    public List<blog> blogsbysql(String sql){
        List<Map<String,Object>> mapList =new ArrayList<Map<String,Object>>();
        try {
            mapList = jdbcTemplate.queryForList(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        List<blog> blogList =new ArrayList<blog>();
        System.out.println("maplist size is:" + mapList.size());
        for (int i = 0; i <mapList.size() ; i++) {
            int blogid = (int)mapList.get(i).get("blogid");
            blog blog = getblogbyid(blogid);
            blogList.add(blog);
        }
        return blogList;
    }

}
