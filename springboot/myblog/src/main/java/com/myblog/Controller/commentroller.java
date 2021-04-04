package com.myblog.Controller;

import com.myblog.Dao.blogDao;
import com.myblog.Dao.userDao;
import com.myblog.pojo.blog;
import com.myblog.pojo.commen;
import com.myblog.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.myblog.Dao.commenDao;

import javax.servlet.http.HttpServletRequest;

@Controller
public class commentroller {
    @Autowired
    commenDao commenDao;
    @Autowired
    userDao userDao;
    @Autowired
    blogDao blogDao;
    @PostMapping("/addcomment")
    @ResponseBody
    public int addcomment(@RequestParam("toblogid")int toblogid,@RequestParam("touserid")int touserid,@RequestParam("userid")int userid,@RequestParam("contain")String contain){
        System.out.println("1");
        commenDao.addcommen(toblogid,touserid,userid,contain);
        System.out.println("2");
        return 1;
    }
    @GetMapping("/directreply/{commenid}")
    public String directreply(@PathVariable("commenid")int commenid, HttpServletRequest request, Model model){
        commen commen = commenDao.getcommenbyid(commenid);
        int useridR = commen.getUserid();
        user userR = userDao.searchbyid(useridR);
        int headimgR = userR.getImg();
        String usernameR = userR.getUsername();

        int blogid = commen.getToblogid();
        blog blog = blogDao.getblogbyid(blogid);
        String blogtitle = blog.getTitle();

        String commencotain = commen.getCommencontain();
        model.addAttribute("headimgR",headimgR);
        model.addAttribute("usernameR",usernameR);
        model.addAttribute("useridR",useridR);
        model.addAttribute("blogid",blogid);
        model.addAttribute("blogtitle",blogtitle);
        model.addAttribute("commencotain",commencotain);
        return "/directreply";
    }
}
