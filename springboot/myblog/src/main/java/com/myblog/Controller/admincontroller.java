package com.myblog.Controller;

import com.myblog.Dao.blogDao;
import com.myblog.Dao.chatDao;
import com.myblog.Dao.tagDao;
import com.myblog.pojo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.myblog.Dao.userDao;
import com.myblog.Dao.chatDao;
import com.myblog.Dao.userphotoDao;
import com.myblog.Dao.commenDao;

import javax.servlet.http.HttpServletRequest;

@Controller
public class admincontroller {
    @Autowired
    blogDao blogDao;
    @Autowired
    userDao userDao;
    @Autowired
    tagDao tagDao;
    @Autowired
    chatDao chatDao;
    @Autowired
    userphotoDao userphotoDao;
    @Autowired
    commenDao commenDao;
    private int commeninonepage = 3;
    private int bloginonepage = 3;
    private int userinonepage = 3;
    private int photoinonepage = 3;
    private int chatinonepage = 3;
    private int taginonepage = 3;
    @GetMapping("/admin/bloglist")
    public String tobloglist(Model model){
        List<blog> blogList = blogDao.getallblogs();
        List<Map<String,Object>> mapList = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < blogList.size(); i++) {
            Map<String,Object> map = new HashMap<String,Object>();
            blog blog = blogList.get(i);
            int blogid = blog.getBlogid();
            String blogtitle = blog.getTitle();
            LocalDateTime localDateTime = blog.getTime();
            int userid = blog.getUserid();
            user user = userDao.searchbyid(userid);
            String username = user.getUsername();
            map.put("blogid",blogid);
            map.put("blogtitle",blogtitle);
            map.put("username",username);
            map.put("time",localDateTime);
            mapList.add(map);
        }
        model.addAttribute("bloglist",mapList);
        model.addAttribute("bloginonepage",bloginonepage);
        model.addAttribute("blogsize",mapList.size());
        return "/admin/bloglist";
    }
    @GetMapping("/admin/manageblog/{blogid}")
    public String tomanageblog(@PathVariable("blogid")int blogid,Model model){
        blog blog = blogDao.getblogbyid(blogid);
        int userid = blog.getUserid();
        user user = userDao.searchbyid(userid);
        int imghead = user.getImg();
        String username = user.getUsername();
        String title = blog.getTitle();
        String contain = blog.getContain();

        List<tag> tagListactive = blogDao.searchtagsbyblog(blogid);
        List<Integer> tagsvalue = new ArrayList<Integer>();
        for(int i=0;i<tagListactive.size();i++){
            int j = tagListactive.get(i).getTagid();
            tagsvalue.add(j);
        }
        List<tag> tagList = tagDao.getalltags();

        model.addAttribute("userid",userid);
        model.addAttribute("blogid",blogid);
        model.addAttribute("imghead",imghead);
        model.addAttribute("username",username);
        model.addAttribute("title",title);
        model.addAttribute("contain",contain);
        model.addAttribute("taglist",tagList);
        model.addAttribute("tagsvalue",tagsvalue);
        return "/admin/manageblog";
    }
    @PostMapping("/admin/editblog")
    public String editblog(@RequestParam("tags[]")int[] tags, @RequestParam("blogid")int blogid, @RequestParam("userid")int userid, @RequestParam("reason")String contain, HttpServletRequest request){
        List<Integer> tagvaluelist = new ArrayList<Integer>();
        for (int i = 0; i < tags.length; i++) {
            tagvaluelist.add(tags[i]);
        }
        int adminid = (int)request.getSession().getAttribute("id");
        blog blog = blogDao.getblogbyid(blogid);
        String title = blog.getTitle();
        String reason = "您的帖子:"+title+",因"+contain+"，被修改";
        blogDao.reeditblogtags(blogid,tagvaluelist);
        chatDao.addchat(adminid,userid,reason);
        return "/admin/bloglist";
    }
    @PostMapping("/admin/delblog")
    public String delblog(@RequestParam("blogid")int blogid,@RequestParam("userid")int userid,@RequestParam("reason")String contain,HttpServletRequest request){
        int adminid = (int)request.getSession().getAttribute("id");
        blog blog = blogDao.getblogbyid(blogid);
        String title = blog.getTitle();
        String reason = "您的帖子:"+title+",因"+contain+"，被删除";
        blogDao.delblog(blogid,userid);
        chatDao.addchat(adminid,userid,reason);
        return "/admin/bloglist";
    }
    @GetMapping("/admin/userlist")
    public String touserlist(Model model){
        List<user> userList = userDao.getalluser();
        List<user> userList1 = new ArrayList<user>();
        for (int i = 0; i < userList.size(); i++) {
            user user = userList.get(i);
            if(user.getLevel() == 1) continue;
            userList1.add(user);
        }
        model.addAttribute("userlist",userList1);
        model.addAttribute("usercount",userList.size());
        model.addAttribute("userinonepage",userinonepage);
        return "/admin/manageusers";
    }
    @GetMapping("/admin/editusermsg/{userid}")
    public String toeditusermsg(@PathVariable("userid")int userid,Model model){
        user user = userDao.searchbyid(userid);
        model.addAttribute("user",user);
        return "/admin/editusermsg";
    }
    @PostMapping("/admin/changeusermsg")
    @ResponseBody
    public String editusermsg(@RequestParam("id")int touserid,@RequestParam("username")String username,@RequestParam("headimg")int headimg,@RequestParam("email")String email,@RequestParam("reason")String reason,HttpServletRequest request){
        userDao.changemsg(touserid,username,email,headimg);
        int adminid = (int)request.getSession().getAttribute("id");
        String reason1 = "您的个人信息:因"+reason+"，被删除";
        chatDao.addchat(adminid,touserid,reason1);
        return "/admin/userlist";
    }
    @GetMapping("/admin/photos/{userid}")
    public String touserphotos(@PathVariable("userid")int touserid,Model model){
        user user = userDao.searchbyid(touserid);
        model.addAttribute("user",user);
        List<Integer> userphotoList = new ArrayList<Integer>();
        userphotoList = userphotoDao.getallphotobyid(touserid);
        model.addAttribute("photolist",userphotoList);
        model.addAttribute("photoinpnepage",photoinonepage);
        model.addAttribute("photocount",userphotoList.size());
        return "/admin/photos";
    }
    @PostMapping("/admin/deluserphoto")
    @ResponseBody
    public String delphoto(@RequestParam("userid")int touserid,@RequestParam("imgid")int imgid,@RequestParam("reason")String reason,HttpServletRequest request){
        int adminid =(int)request.getSession().getAttribute("id");
        userphotoDao.delphoto(touserid,imgid);
        String reason1 = "您的一张图片因为："+reason+"，被删除";
        chatDao.addchat(adminid,touserid,reason1);
        return "/admin/userlist";
    }
    @PostMapping("/admin/changeenable")
    @ResponseBody
    public String changeanable(@RequestParam("userid")int touserid,@RequestParam("enable")int enable){
        userDao.setenable(touserid,enable);
        return "/admin/userlist";
    }
    @GetMapping("/admin/tocommenlist")
    public String tocommenlist(Model model){
        List<commen>  commenList = new ArrayList<commen>();
        commenList = commenDao.getallcommens();
        List<Map<String,Object>> commeninfolist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < commenList.size(); i++) {
            int commenid = commenList.get(i).getCommenid();
            int userid = commenList.get(i).getUserid();
            user user = userDao.searchbyid(userid);
            String username =user.getUsername();
            LocalDateTime time = commenList.get(i).getTime();
            String contain = commenList.get(i).getCommencontain();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("commenid",commenid);
            map.put("userid",userid);
            map.put("username",username);
            map.put("time",time);
            map.put("contain",contain);
            commeninfolist.add(map);
        }
        model.addAttribute("commenlist",commeninfolist);
        model.addAttribute("commencount",commenList.size());
        model.addAttribute("commeninonepage",commeninonepage);
        return "/admin/commens";
    }
    @PostMapping("/admin/delcommen")
    @ResponseBody
    public String delcommen(HttpServletRequest request,@RequestParam("userid")int userid,@RequestParam("commenid")int commenid,@RequestParam("reason")String reason){
        int adminid = (int)request.getSession().getAttribute("id");
        String reason1 = "您的一条评论因为："+reason+"，被删除";
        chatDao.addchat(adminid,userid,reason1);
        commenDao.delcommenbyid(commenid);
        return "/admin/tocommenlist";
    }
    @GetMapping("/admin/chatlist")
    public String tochatlist(Model model){
        List<chat> chatlist = new ArrayList<chat>();
        chatlist = chatDao.getallchats();
        List<Map<String,Object>> chatinfolist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < chatlist.size(); i++) {
            int chatid =  chatlist.get(i).getLetterid();
            int userid = chatlist.get(i).getUserid();
            user user = userDao.searchbyid(userid);
            if(user.getLevel() == 1) continue;
            String username = user.getUsername();
            LocalDateTime localDateTime = chatlist.get(i).getTime();
            String contain = chatlist.get(i).getContain();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("chatid",chatid);
            map.put("userid",userid);
            map.put("username",username);
            map.put("time",localDateTime);
            map.put("contain",contain);
            chatinfolist.add(map);
        }

        model.addAttribute("chatlist",chatinfolist);
        model.addAttribute("chatlistcount",chatinfolist.size());
        model.addAttribute("chatinonepage",chatinonepage);
        return "/admin/chats";
    }
    @PostMapping("/admin/delchat")
    @ResponseBody
    public String delchat(HttpServletRequest request,@RequestParam("userid")int userid,@RequestParam("chatid")int chatid,@RequestParam("reason")String reason){
        int adminid = (int)request.getSession().getAttribute("id");
        String reason1 = "您的一条私信因为："+reason+"，被删除";
        chatDao.addchat(adminid,userid,reason1);
        chatDao.delchatbyid(chatid);
        return "/admin/chatlist";
    }
    @GetMapping("/admin/taglist")
    public String totaglist(Model model){
        List<tag> tagList = new ArrayList<tag>();
        tagList = tagDao.getalltags();
        model.addAttribute("taglist",tagList);
        model.addAttribute("taglistcount", tagList.size());
        model.addAttribute("taginonepage",taginonepage);
        return "/admin/tags";
    }
    @PostMapping("/admin/deltag")
    @ResponseBody
    public String deltag(@RequestParam("tag")int tagid){
        System.out.println(tagid);
        tagDao.deltagbyid(tagid);
        return "/admin/taglist";
    }
    @PostMapping("/admin/addtag")
    @ResponseBody
    public int addtag(@RequestParam("tagname")String tagname){
        System.out.println(tagDao.havesamename(tagname));
        if(tagDao.havesamename(tagname)) return 1;
        else {
            tagDao.addtag(tagname);
            return 2;
        }
        //tagDao.ad
    }
}
