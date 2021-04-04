package com.myblog.Controller;

import com.myblog.pojo.blog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.myblog.Dao.blogDao;
import com.myblog.Dao.tagDao;
import com.myblog.pojo.tag;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Array;
import java.util.*;

@Controller
public class editblogcontroller {

    @Autowired
    blogDao blogDao;
    @Autowired
    tagDao tagDao;
    @GetMapping("/toaddblog")
    public String addblogpage(HttpServletRequest request, Model model){
        List<tag> tagList = tagDao.getalltags();
        model.addAttribute("taglist",tagList);
        return "/editblog";
    }
    @PostMapping("/postblog")
    @ResponseBody
    public int addblog(@RequestParam("userid")int userid, @RequestParam("title") String title, @RequestParam("tags[]")int[] tags,@RequestParam("contain")String contain){
        System.out.println("userid is :" + userid);
        System.out.println("title is :" + title);
        System.out.println("tags are :" + tags[0]);
        System.out.println("contain is :" + contain);
        List<Integer> taglist = new ArrayList<Integer>();
        for (int i= 0;i<tags.length;i++){
            taglist.add(tags[i]);
        }

        blogDao.addblog(userid,title,contain,0,taglist);
        return blogDao.getmaxblogid();
    }
    @GetMapping("/editblog/{blogid}")
    public String toeditblog(@PathVariable("blogid")int blogid, HttpServletRequest request,Model model){
        int userid = (int) request.getSession().getAttribute("id");
        if(blogDao.getblogbyid(blogid,userid)==null){
            System.out.println("不是你的博客不允许修改");
            return "/myblog";
        }else{
            blog blog = blogDao.getblogbyid(blogid,userid);
            List<tag> tagListactive = blogDao.searchtagsbyblog(blogid);
            List<Integer> tagsvalue = new ArrayList<Integer>();
            for(int i=0;i<tagListactive.size();i++){
                int j = tagListactive.get(i).getTagid();
                tagsvalue.add(j);
            }
            List<tag> tagList = tagDao.getalltags();
            model.addAttribute("blog",blog);
            model.addAttribute("blogid",blogid);
            model.addAttribute("taglist",tagList);
            model.addAttribute("tagsvalue",tagsvalue);
            model.addAttribute("title",blog.getTitle());
            model.addAttribute("contain",blog.getContain());
            return "/reeditblog";
        }
    }
    @PostMapping("/postreeditblog")
    public String reeditblog(@RequestParam("tags[]")int[] tags,@RequestParam("title")String title,@RequestParam("contain")String contain,@RequestParam("blogid")int blogid){
        blogDao.reeditblog(blogid,title,contain);
        List<Integer> taglist = new ArrayList<Integer>();
        for (int i= 0;i<tags.length;i++){
            taglist.add(tags[i]);
        }
        blogDao.reeditblogtags(blogid,taglist);
        return "myblog";
    }


}
