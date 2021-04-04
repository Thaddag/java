package com.myblog.Dao;
import com.myblog.pojo.tag;
import com.myblog.pojo.blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class tagDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
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
    public List<tag> getalltags(){
        String sql = "select * from tag";
        List<Map<String,Object>> maplist= null;
        List<tag> tagList = new ArrayList<tag>();
        try {
            System.out.println("1");
            maplist = jdbcTemplate.queryForList(sql);
            System.out.println("2");
        } catch (EmptyResultDataAccessException e) {
            System.out.println("3");
            return null;
        }
        for (int i = 0; i<maplist.size();i++){
            System.out.println("4");
            Map<String,Object> map = maplist.get(i);
            tag tag = new tag((int)map.get("tagid"),(String) map.get("tagname"));
            System.out.println("5");
            System.out.println(tag.toString());
            tagList.add(tag);
            System.out.println("6");
        }
        return tagList;
    }
    public void settag(String tagname){
        String sql ="inset into tag tagname =?";
        jdbcTemplate.execute(sql);
    }
    public List<blog> searchblogbytag(int tagid){
        String sql ="select * from blog_tag where tagid = ?";
        List<Map<String,Object>> list_map = null;
        try {
            list_map = jdbcTemplate.queryForList(sql,tagid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        List<blog> blogList = new ArrayList<blog>();
        Map<String,Object> map =null;
        for(int i=0;i<list_map.size();i++ ){
            map = list_map.get(i);
            int blogid = (int) map.get("blogid");
            blog blog = getblogbyid(blogid);
            blogList.add(blog);
        }
        return blogList;
    }
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
    public void deltagbyid(int tagid){
        String sql ="delete from tag where tagid = "+tagid;
        jdbcTemplate.execute(sql);
        String sql2 = "delete from blog_tag where tagid = "+tagid;
        jdbcTemplate.execute(sql2);
    }
    public void addtag(String tagname){
        String sql = "insert into tag(tagname) value ('"+tagname+"')";
        jdbcTemplate.execute(sql);
    }
    public boolean havesamename(String tagname){
        String sql ="select * from tag where tagname ='"+tagname+"'";
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            map = jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;

    }
}
