package com.myblog.Dao;
import com.myblog.pojo.emailcheck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Map;
import java.util.Random;
@Repository
public class emailcheckDao {
    @Autowired
    JdbcTemplate jdbcTemplate;
    public int isoutdate(String emailaddress,String checkcode){
        String sql = "select * from emil_check where email = ? and emailcode = ?";
        Object[] s = new Object[2];
        s[0] = emailaddress;
        s[1] = checkcode;
        Map map = null;
        try {
            map = jdbcTemplate.queryForMap(sql,s);
        } catch (EmptyResultDataAccessException e) {
            return 0;
            //e.printStackTrace();
        }
        emailcheck emailcheck = new emailcheck((String)map.get("email"),(String) map.get("emailcode"),(LocalDateTime)map.get("createdate"));
        if(Duration.between(emailcheck.getCreatetime(),LocalDateTime.now()).toMinutes() > 5){
            return 1;//验证码过期
        }else return 2;//验证码没过期
    }

    public void setcode(String emailaddress,String checkcode){
        String sql = "insert into emil_check(email,emailcode,createdate) values(?,?,?)";
        Object[] objects = new Object[3];
        objects[0] = emailaddress;
        objects[1] = checkcode;
        /*Timestamp timestamp = new Timestamp(new Date().getTime());
        objects[2] = timestamp;*/
        objects[2] = LocalDateTime.now();
        jdbcTemplate.update(sql,objects);
    }

}
