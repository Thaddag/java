package com.myblog.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.myblog.pojo.commen;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Repository
public class commenDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public void addcommen(int toblogid,int touserid, int userid, String commencontain){
        String sql = "insert into commen(toblogid,touserid,userid,contain,time) value (?,?,?,?,?)";
        Object[] objects =new Object[5];
        objects[0] = toblogid;
        objects[1] = touserid;
        objects[2] = userid;
        objects[3] = commencontain;
        objects[4] = new Date();
        jdbcTemplate.update(sql,objects);
    }
    public commen getcommenbyid(int commenid){
        String sql = "select * from commen where commenid = ?";
        Map<String,Object> map = null;
        map =jdbcTemplate.queryForMap(sql,commenid);
        commen commen = new commen((int)map.get("commenid"),
                (int)map.get("toblogid"),
                (int)map.get("touserid"),
                (int)map.get("userid"),
                (String)map.get("contain"),
                (LocalDateTime)map.get("time"));
        return commen;
    }
    public List<commen> getcommenunderblog(int tocommenblogid){
        String sql ="select * from commen where toblogid = "+tocommenblogid;
        List<commen> commenList = new ArrayList<commen>();
        commenList = getcommenbysql(sql);
        return commenList;
    }
    public List<commen> getcommensreply(int userid){
        String sql = "select * from commen where userid = "+userid+" order by commenid desc";
        List<commen> commenList = new ArrayList<commen>();
        commenList = getcommenbysql(sql);
        return commenList;

    }
    public void delcommensbyblog(int blogid){
        String sql ="delete from commen where toblogid = ?";
        jdbcTemplate.update(sql, blogid);
    }
    public List<commen> getallcommens(){
        String sql ="select * from commen";
        List<commen> commenList = new ArrayList<commen>();
        commenList = getcommenbysql(sql);
        return commenList;
    }
    public List<commen> getcommenbysql (String sql){
        List<commen> commenList = new ArrayList<commen>();
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        mapList = jdbcTemplate.queryForList(sql);
        for (int i = 0; i < mapList.size(); i++) {
            int commenid =(int)mapList.get(i).get("commenid");
            commen commen = getcommenbyid(commenid);
            commenList.add(commen);
        }
    return commenList;
    }
    public void delcommenbyid(int commenid){
        String sql = "delete from commen where commenid = "+commenid;
        jdbcTemplate.execute(sql);
    }
}
