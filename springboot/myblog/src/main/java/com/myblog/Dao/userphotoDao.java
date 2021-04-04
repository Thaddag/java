package com.myblog.Dao;
import com.myblog.pojo.userphoto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Repository
public class userphotoDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public List<Integer> getallphotobyid(int userid){
        String sql = "select * from userphoto where userid = ?";
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        try {
            mapList = jdbcTemplate.queryForList(sql,userid);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        List<Integer> imglist = new ArrayList<Integer>();
        for(int i = 0; i<mapList.size();i++){
            int img = (int) mapList.get(i).get("imgid");
            imglist.add(img);
        }
        return imglist;
    }
    public void addphoto(int userid,int imgid){
        String sql = "insert into userphoto (userid,imgid) value (?,?)";
        Object[] objects = new Object[2];
        objects[0] = userid;
        objects[1] = imgid;
        jdbcTemplate.update(sql,objects);
    }
    public void delphoto(int userid,int imgid){
        String sql = "delete from userphoto where userid = ? and imgid = ?";
        Object[] objects = new Object[2];
        objects[0] = userid;
        objects[1] = imgid;
        jdbcTemplate.update(sql,objects);
    }
}
