package com.myblog.Dao;

import com.myblog.pojo.chat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

@Repository
public class chatDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<chat> getchats(int userid,int touserid){
        String sql = "select * from letter where (userid = "+userid+" and touserid = "+touserid+") or (userid = "+touserid+" and touserid = "+userid+") order by time";
        List<chat> chatlist = new ArrayList<chat>();
        chatlist = getchatsbysql(sql);
        return chatlist;
    }
    public List<chat> getchats(int userid){
        String sql = "select * from letter where touserid = "+userid+" order by time";

        List<chat> chatlist = new ArrayList<chat>();
        chatlist = getchatsbysql(sql);
        return chatlist;
    }
    public List<chat> getallchats(){
        String sql ="select * from letter";
        List<chat> chatlist = new ArrayList<chat>();
        chatlist = getchatsbysql(sql);
        return chatlist;
    }
    public void delchatbyid(int chatid){
        String sql ="delete from letter where letterid = "+chatid;
        jdbcTemplate.execute(sql);
    }
    public void addchat(int userid,int touserid,String contain){
        String sql ="insert into letter (userid,touserid,contain,time) value (?,?,?,?)";
        Object[] objects = new Object[4];
        objects[0] = userid;
        objects[1] = touserid;
        objects[2] = contain;
        objects[3] = new Date();
        jdbcTemplate.update(sql,objects);
     }
    public chat getchatbyid(int chatid){
        String sql = "select * from letter where letterid = "+chatid;
        Map<String,Object> map = new HashMap<String,Object>();
        try {
            map = jdbcTemplate.queryForMap(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        chat chat = new chat((int)map.get("letterid"),(int)map.get("userid"),(int)map.get("touserid"),(String)map.get("contain"),(LocalDateTime)map.get("time"));
        return chat;
    }
    public List<chat> getchatsbysql(String sql){
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        try {
            mapList = jdbcTemplate.queryForList(sql);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

        List<chat> chatlist = new ArrayList<chat>();
        for (int i = 0; i < mapList.size(); i++) {
            chat chat = getchatbyid((int)mapList.get(i).get("letterid"));
            chatlist.add(chat);
        }
        return chatlist;
    }
}
