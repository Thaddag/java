package com.myblog.Controller;

import com.myblog.Dao.blacklistDao;
import com.myblog.Dao.chatDao;
import com.myblog.Dao.userDao;
import com.myblog.pojo.chat;
import com.myblog.pojo.user;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class chatcontroller {
    @Autowired
    userDao userDao;
    @Autowired
    com.myblog.Dao.chatDao chatDao;
    @Autowired
    blacklistDao blacklistDao;
    private int chatinonepage = 10;
    @GetMapping("/chatto/{touserid}")
    public String tochatbox(@PathVariable("touserid")int touserid, HttpServletRequest request, Model model){

        int userid = (int)request.getSession().getAttribute("id");

        user touser = userDao.searchbyid(touserid);

        user user = userDao.searchbyid(userid);
        int meinblacklist = blacklistDao.ismeblacklist(userid,touserid);
        List<chat> chatList = chatDao.getchats(userid,touserid);
        System.out.println("chatlist size is :" + chatList.size());
        model.addAttribute("chatList",chatList);
        model.addAttribute("touserid",touserid);
        model.addAttribute("touser",touser);
        model.addAttribute("meinblacklist",meinblacklist);
        model.addAttribute("user",user);
        return "/chatbox";
    }
    @PostMapping("/postchat")
    public String postchat(@RequestParam("touserid")int touserid,@RequestParam("contain")String contain,HttpServletRequest request){

        int userid = (int)request.getSession().getAttribute("id");

        chatDao.addchat(userid,touserid,contain);

        return "redirect:/chatto/"+touserid;
    }
    @GetMapping("/chat")
    public String tochatlist(HttpServletRequest request,Model model){
        int userid = (int)request.getSession().getAttribute("id");
        List<chat> chatList = chatDao.getchats(userid);
        List<Map<String,Object>> chatinfolist = new ArrayList<Map<String,Object>>();
        for (int i = 0; i < chatList.size(); i++) {
            int Ruserid = (int)chatList.get(i).getUserid();
            user user = userDao.searchbyid(Ruserid);
            int headimg = user.getImg();
            String username = user.getUsername();
            LocalDateTime time = (LocalDateTime) chatList.get(i).getTime();
            String contain = (String)chatList.get(i).getContain();
            int chatid = (int)chatList.get(i).getLetterid();
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("Ruserid",Ruserid);
            map.put("imgid",headimg);
            map.put("username",username);
            map.put("time",time);
            map.put("contain",contain);
            map.put("chatid",chatid);
            chatinfolist.add(map);
        }
        model.addAttribute("chatcount",chatList.size());
        model.addAttribute("chatinonepage",chatinonepage);
        model.addAttribute("chatlist",chatinfolist);
        return "/chat";
    }
}
