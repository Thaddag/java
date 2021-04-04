package com.myblog.Controller;

import com.myblog.Dao.tagDao;
import com.myblog.pojo.blog;
import com.myblog.pojo.commen;
import com.myblog.pojo.tag;
import com.myblog.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import com.myblog.Dao.blogDao;
import com.myblog.Dao.userDao;
import com.myblog.Dao.commenDao;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.myblog.Dao.tagDao;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class blogcontroller {
    @Autowired
    blogDao blogDao;
    @Autowired
    userDao userDao;
    @Autowired
    commenDao commenDao;
    @Autowired
    tagDao tagDao;

    private int commen_in_onpage = 4;
    private int bloginonpage = 15;

    @GetMapping("/blog/{blogid}")
    public String toblog(@PathVariable("blogid")int blogid,Model model){
        blog blog = blogDao.getblogbyid(blogid);
        List<Map<String,Object>> commenmapList = new ArrayList<Map<String,Object>>();
        List<commen> commenList = new ArrayList<commen>();
        if(commenDao.getcommenunderblog(blogid) == null){
            System.out.println("无评论");
            model.addAttribute("pagecount",0);
        }else {
            commenList = commenDao.getcommenunderblog(blogid);
        }

        for(int i =0;i<commenList.size();i++){
            commen commen = commenList.get(i);
            int touserid = commen.getTouserid();
            int userid = commen.getUserid();
            user touser = userDao.searchbyid(touserid);
            user user = userDao.searchbyid(userid);
            String tousername = touser.getUsername();
            String username = user.getUsername();
            int userheadimg = user.getImg();
            //int userid = user.getId();
            System.out.println("geted liange name");
            Map<String,Object> map = new HashMap<String,Object>();
            System.out.println("getmap init");
            map.put("commen",commen);
            map.put("tousername",tousername);
            map.put("username",username);
            map.put("userid",userid);
            map.put("userheadimg",userheadimg);
            System.out.println("load map is:" + map.toString());
            commenmapList.add(map);
        }

        user user = userDao.searchbyid(blog.getUserid());
        model.addAttribute("blogid",blog.getBlogid());
        model.addAttribute("userid",blog.getUserid());
        model.addAttribute("title",blog.getTitle());
        model.addAttribute("username",user.getUsername());
        model.addAttribute("userheadimg",user.getImg());
        model.addAttribute("blogtime",blog.getTime());
        model.addAttribute("contain",blog.getContain());


        model.addAttribute("tags",blogDao.searchtagsbyblog(blogid));


        model.addAttribute("commens",commenmapList);
        model.addAttribute("commeninonepage",commen_in_onpage);
        model.addAttribute("commencount",commenList.size());
        //侧边栏tag
        List<tag> tagList = tagDao.getalltags();
        model.addAttribute("taglist",tagList);

        return "/blog";
    }
    @GetMapping("/myblog")
    public String myblog(HttpServletRequest request,Model model){
        int userid = (int)request.getSession().getAttribute("id");
        List<blog> blogList = new ArrayList<blog>();
        if(blogDao.getuserblogsbyuserid(userid) == null){
            System.out.println("未发表博客");
        }else {
            blogList =blogDao.getuserblogsbyuserid(userid);
        }
        model.addAttribute("bloglist",blogList);
        model.addAttribute("blogsize",blogList.size());
        model.addAttribute("bloginonepage",bloginonpage);
        return "/myblog";
    }
    @GetMapping("/delblog/{blogid}")
    public String delblog(@PathVariable("blogid")int blogid,HttpServletRequest request){
        int userid = (int)request.getSession().getAttribute("id");
        if(blogDao.delblog(blogid,userid)){
            commenDao.delcommensbyblog(blogid);
            blogDao.deltagsbyblog(blogid);
        }else {
            System.out.println("删除失败");
        }

        return "/myblog";
    }
    @PostMapping("/changetopstat")
    public String changetop(@RequestParam("blogid")int blogid,@RequestParam("top")int top){
        blogDao.settopstat(blogid,top);
        return "/myblog";
    }


}
