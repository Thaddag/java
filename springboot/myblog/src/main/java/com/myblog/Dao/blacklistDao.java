package com.myblog.Dao;

import com.myblog.pojo.blacklist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class blacklistDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void addblacklist(int userid,int touserid){
        String sql = "insert into blacklist(userid,touserid) value(?,?)";
        Object[] objects = new Object[2];
        objects[0] = userid;
        objects[1] = touserid;
        jdbcTemplate.update(sql,objects);
    }
    public int isheblacklist(int userid,int touserid){
        String sql ="select * from blacklist where userid = ? and touserid = ?";
        Object[] objects = new Object[2];
        objects[0] = userid;
        objects[1] = touserid;
        List<Map<String,Object>> maplist = null;
        try {

            maplist = jdbcTemplate.queryForList(sql,objects);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        if(maplist.size()==0) return 0;
        else return 1;
    }
    public int ismeblacklist(int userid,int touserid){
        String sql ="select * from blacklist where userid = ? and touserid = ?";
        Object[] objects = new Object[2];
        objects[0] = touserid;
        objects[1] = userid;
        List<Map<String,Object>> maplist = null;
        try {
            maplist = jdbcTemplate.queryForList(sql,objects);
        } catch (EmptyResultDataAccessException e) {
            return 0;
        }
        if(maplist.size()==0) return 0;
        else return 1;
    }
    public void delblacklist(int userid,int touserid){
        String sql ="delete from blacklist where userid = ? and touserid =?";
        Object[] objects = new Object[2];
        objects[0] = userid;
        objects[1] = touserid;
        jdbcTemplate.update(sql,objects);
    }
    public List<blacklist> getmyblacklist(int userid){
        String sql ="select * from blacklist where userid =?";
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        try {
            mapList = jdbcTemplate.queryForList(sql,userid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        List<blacklist> blacklists =new ArrayList<blacklist>();
        for (int i = 0; i < mapList.size(); i++) {
            blacklist blacklist =new blacklist((int)mapList.get(i).get("userid"),(int)mapList.get(i).get("touserid"));
            blacklists.add(blacklist);
        }
        return blacklists;
    }
}
