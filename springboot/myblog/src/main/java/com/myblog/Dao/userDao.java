package com.myblog.Dao;
import com.myblog.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class userDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void adduser(String username,String userpassword,String email){
        String sql = "insert into users(username,userpassword,email,headimg,level,time,enable) values(?,?,?,?,?,?,?)";
        Object[] objects = new Object[7];
        objects[0] = username;
        objects[1] = userpassword;
        objects[2] = email;
        objects[3] = 1;
        objects[4] = 0;
        objects[5] = new Date();
        objects[6] = 1;
        jdbcTemplate.update(sql,objects);
    }
    public user searchbyname(String username) {
        String sql = "select * from users where username = ?";
        Map<String,Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql,username);
        } catch (EmptyResultDataAccessException e) {
            //e.printStackTrace();
            return null;
        }
        user user = new user((int)map.get("userid"),(String)map.get("username"),(String)map.get("userpassword"),(String)map.get("email"),(int)map.get("headimg"),(int)map.get("level"),(LocalDateTime) map.get("time"),(int)map.get("enable"));
        return user;
    }
    public user searchbyid(int id) {
        String sql = "select * from users where userid = ?";

        Map<String,Object> map = null;
        try {
            map = jdbcTemplate.queryForMap(sql,id);
        } catch (EmptyResultDataAccessException e) {
            //e.printStackTrace();
            return null;
        }
        user user = new user((int)map.get("userid"),(String)map.get("username"),(String)map.get("userpassword"),(String)map.get("email"),(int)map.get("headimg"),(int)map.get("level"),(LocalDateTime) map.get("time"),(int)map.get("enable"));
        return user;
    }
    public void changemsg(int id,String name,String email,int head){
        String sql = "update users set username = ?, email = ?, headimg = ? where userid = ? ";
        Object[] objects = new Object[4];
        objects[0] = name;
        objects[1] = email;
        objects[2] = head;
        objects[3] = id;
        jdbcTemplate.update(sql,objects);
    }
    public void changepassword(int id,String password){
        String sql ="update users set userpassword = ? where userid = ?";
        Object[] objects = new Object[2];
        objects[0]=password;
        objects[1]=id;
        jdbcTemplate.update(sql,objects);
    }
    public List<user> getalluser(){
        List<user> userList = new ArrayList<user>();
        String sql = "select * from users";
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++) {
            int userid = (int)mapList.get(i).get("userid");
            user user = searchbyid(userid);
            userList.add(user);
        }
        return userList;
    }
    public void setenable(int userid,int enable){
        String sql ="update users set enable = "+enable+" where userid = "+userid;
        jdbcTemplate.update(sql);
    }



}
