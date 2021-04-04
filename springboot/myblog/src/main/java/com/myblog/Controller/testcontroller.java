package com.myblog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import com.myblog.Dao.userDao;
import com.myblog.pojo.user;
@RestController
public class testcontroller {
    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    userDao userdao;
    @GetMapping("/test")
    @ResponseBody
    public String test(){
        //userdao.adduser("马报国","123456","111@11.com");
        user user = userdao.searchbyname("马报国");
        /*String sql = "select * from users";
        List<Map<String,Object>> list_map = jdbcTemplate.queryForList(sql);
        System.out.println(list_map.toString());*/

        return user.toString();
    }
}
