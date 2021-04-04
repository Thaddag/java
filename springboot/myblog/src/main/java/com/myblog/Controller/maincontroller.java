package com.myblog.Controller;

import com.myblog.Dao.blogDao;
import com.myblog.Dao.tagDao;
import com.myblog.Dao.userDao;
import com.myblog.pojo.blog;
import com.myblog.pojo.tag;
import com.myblog.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.myblog.Dao.userDao;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class maincontroller {
    @Autowired
    blogDao blogDao;
    @Autowired
    tagDao tagDao;
    @Autowired
    userDao userDao;
    private int bloginonepage = 10;
    @GetMapping("/main")
    public String tomain(Model model){
        List<blog> blogList =new ArrayList<blog>();
        blogList = blogDao.getallblogs();
        List<Map<String,Object>> bloginfolist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < blogList.size(); i++) {
            Map<String,Object> map =new HashMap<String,Object>();
            int blogid = blogList.get(i).getBlogid();
            int userid = blogList.get(i).getUserid();
            user user = userDao.searchbyid(userid);
            String title = blogList.get(i).getTitle();
            String username = user.getUsername();
            int userheadimg = user.getImg();
            String contain = blogList.get(i).getContain();
            LocalDateTime time =blogList.get(i).getTime();
            List<tag> blogtags = blogDao.searchtagsbyblog(blogid);
            map.put("blogid",blogid);
            map.put("title",title);
            map.put("username",username);
            map.put("userheadimg",userheadimg);
            map.put("time",time);
            map.put("contain",contain);
            map.put("blogtags",blogtags);
            bloginfolist.add(map);
        }

        model.addAttribute("bloginfolist",bloginfolist);
        List<tag> tagList = tagDao.getalltags();
        model.addAttribute("taglist",tagList);
        model.addAttribute("bloginonepage",bloginonepage);
        model.addAttribute("blogcount",bloginfolist.size());
     return "/main";
    }
    @GetMapping("/tag/{tagid}")
    public String blogsbytag(@PathVariable("tagid")int tagid,Model model){
        List<blog> blogList =new ArrayList<blog>();
        blogList = blogDao.getblogsbytag(tagid);
        List<Map<String,Object>> bloginfolist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < blogList.size(); i++) {
            Map<String,Object> map =new HashMap<String,Object>();
            int blogid = blogList.get(i).getBlogid();
            int userid = blogList.get(i).getUserid();
            user user = userDao.searchbyid(userid);
            String title = blogList.get(i).getTitle();
            String username = user.getUsername();
            int userheadimg = user.getImg();
            String contain = blogList.get(i).getContain();
            LocalDateTime time =blogList.get(i).getTime();
            List<tag> blogtags = blogDao.searchtagsbyblog(blogid);
            map.put("blogid",blogid);
            map.put("title",title);
            map.put("username",username);
            map.put("userheadimg",userheadimg);
            map.put("time",time);
            map.put("contain",contain);
            map.put("blogtags",blogtags);
            bloginfolist.add(map);
        }

        model.addAttribute("bloginfolist",bloginfolist);
        List<tag> tagList = tagDao.getalltags();
        model.addAttribute("taglist",tagList);
        model.addAttribute("bloginonepage",bloginonepage);
        model.addAttribute("blogcount",bloginfolist.size());
        return "/main";
    }
    @GetMapping("/search/{s}")
    public String blogsbysearch(@PathVariable("s")String s,Model model){
        List<blog> blogList =new ArrayList<blog>();
        blogList = blogDao.searchtitlelike(s);
        List<Map<String,Object>> bloginfolist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < blogList.size(); i++) {
            Map<String,Object> map =new HashMap<String,Object>();
            int blogid = blogList.get(i).getBlogid();
            int userid = blogList.get(i).getUserid();
            user user = userDao.searchbyid(userid);
            String title = blogList.get(i).getTitle();
            String username = user.getUsername();
            int userheadimg = user.getImg();
            String contain = blogList.get(i).getContain();
            LocalDateTime time =blogList.get(i).getTime();
            List<tag> blogtags = blogDao.searchtagsbyblog(blogid);
            map.put("blogid",blogid);
            map.put("title",title);
            map.put("username",username);
            map.put("userheadimg",userheadimg);
            map.put("time",time);
            map.put("contain",contain);
            map.put("blogtags",blogtags);
            bloginfolist.add(map);
        }

        model.addAttribute("bloginfolist",bloginfolist);
        List<tag> tagList = tagDao.getalltags();
        model.addAttribute("taglist",tagList);
        model.addAttribute("bloginonepage",bloginonepage);
        model.addAttribute("blogcount",bloginfolist.size());
        return "/main";
    }

}
