package com.myblog.Controller;

import com.myblog.Dao.*;
import com.myblog.pojo.blacklist;
import com.myblog.pojo.blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.myblog.Dao.blogDao;
import com.myblog.pojo.user;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class usercontroller {
    @Autowired
    userDao userDao;
    @Autowired
    blogDao blogDao;
    @Autowired
    userphotoDao userphotoDao;
    @Autowired
    blacklistDao blacklistDao;
    private int bloginonepage = 5;

    /*@GetMapping("/editmsg")
    public String toeditmsg(){
        return "/editmsg";
    }*/

    @PostMapping("/changeusermsg")
    @ResponseBody
    public void changeusermsg(HttpServletRequest request, @RequestParam("id") int id, @RequestParam("username") String name, @RequestParam("headimg")int headimg, @RequestParam("email")String email){
        //System.out.println("id:"+id+",name:" + name +",headimg:"+ headimg+",email" + email);
        userDao.changemsg(id,name,email,headimg);
        request.getSession().setAttribute("name",name);
        request.getSession().setAttribute("headimg",headimg);
        request.getSession().setAttribute("email",email);
    }
    @PostMapping("/changepassword")
    @ResponseBody
    public void changepassword(@RequestParam("id") int id,@RequestParam("password") String password){
        userDao.changepassword(id,password);
    }
    @GetMapping("/signout")
    public String signout(HttpServletRequest request){
        request.getSession().removeAttribute("id");
        request.getSession().removeAttribute("headimg");
        request.getSession().removeAttribute("email");
        request.getSession().removeAttribute("level");
        request.getSession().removeAttribute("name");
        return "/login";
    }

    @GetMapping("/personalpage/{touserid}")
    public String topersonpage(@PathVariable("touserid")int touserid,HttpServletRequest request, Model model){
        user user = userDao.searchbyid(touserid);
        List<blog> bloglist = blogDao.getuserblogsbyuserid(touserid);
        List<Integer> photolist = userphotoDao.getallphotobyid(touserid);
        int blogscount = blogDao.getblogcountbyid(touserid);
        int userid = (int)request.getSession().getAttribute("id");
        int isblacklist = blacklistDao.isheblacklist(userid,touserid);
        model.addAttribute("isblacklist",isblacklist);
        model.addAttribute("user",user);
        model.addAttribute("blogscount",blogscount);
        model.addAttribute("bloglist",bloglist);
        model.addAttribute("photolist",photolist);
        model.addAttribute("bloginonepage",bloginonepage);
        return "/personalpage";
    }
}
