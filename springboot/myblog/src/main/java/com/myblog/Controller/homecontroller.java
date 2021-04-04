package com.myblog.Controller;

import com.myblog.pojo.blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import com.myblog.Dao.blogDao;
import com.myblog.pojo.user;
import com.myblog.Dao.userDao;
import com.myblog.Dao.commenDao;
import com.myblog.pojo.commen;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class homecontroller {
    @Autowired
    blogDao blogDao;
    @Autowired
    userDao userDao;
    @Autowired
    commenDao commenDao;
    private int commeninonepage = 2;

    @GetMapping("/home")
    public String tohome(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        session.getAttribute("username");

        int userid =(int)session.getAttribute("id");
        //request.getSession().getAttribute("headimg");
        //String name = (String)request.getSession().getAttribute("name");
        //String email = (String)request.getSession().getAttribute("email");
        int myblogcount = blogDao.getblogcountbyid(userid);
        user user = userDao.searchbyid(userid);
        LocalDateTime entertime = user.getTime();
        List<Map<String,Object>> commenlistR = new ArrayList<Map<String,Object>>();
        if(commenDao.getcommensreply(userid)==null){
            System.out.println("无回复");
        }else {
            List<commen> commenList= commenDao.getcommensreply(userid);
            for (int i = 0;i<commenList.size();i++){
                int userRid = commenList.get(i).getUserid();
                user userR = userDao.searchbyid(userRid);
                String userRname = userR.getUsername();
                int blogRid = commenList.get(i).getToblogid();
                blog blogR = blogDao.getblogbyid(blogRid);
                System.out.println("blogrid = "+blogRid);
                String blogRtitle = blogR.getTitle();
                System.out.println("2");
                String containR = commenList.get(i).getCommencontain();
                LocalDateTime timeR = commenList.get(i).getTime();
                Map<String,Object> map = new HashMap<String,Object>();
                map.put("commenid",commenList.get(i).getCommenid());
                map.put("usernameR",userRname);
                map.put("titleR",blogRtitle);
                map.put("containR",containR);
                map.put("timeR",timeR);
                commenlistR.add(map);
            }
        }
        model.addAttribute("myblogcount",myblogcount);
        model.addAttribute("entertime",entertime);
        model.addAttribute("commenlist",commenlistR);
        model.addAttribute("commensize",commenlistR.size());
        model.addAttribute("commeninonepage",commeninonepage);
        System.out.println("there is");
        return "/home";
    }
}
