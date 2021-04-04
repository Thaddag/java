package com.myblog.Controller;

import com.myblog.Dao.userDao;
import com.myblog.pojo.blacklist;
import com.myblog.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.myblog.Dao.blacklistDao;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class blacklistcontroller {
    @Autowired
    blacklistDao blacklistDao;
    @Autowired
    com.myblog.Dao.userDao userDao;
    private int blacklistinonepage = 10;
    @GetMapping("/addblacklist/{id}")
    public String addblacklist(@PathVariable("id")int touserid, HttpServletRequest request, Model model){
        int userid = (int)request.getSession().getAttribute("id");
        blacklistDao.addblacklist(userid,touserid);
        return "redrict:/personalpage/"+touserid;
    }
    @GetMapping("/blacklist")
    public String myblacklist(HttpServletRequest request,Model model){
        System.out.println("blacklist in");
        int userid =  (int)request.getSession().getAttribute("id");
        List<blacklist> blacklists = blacklistDao.getmyblacklist(userid);
        List<Map<String,Object>> blackinfolist = new ArrayList<Map<String,Object>>();
        System.out.println("blacklist ready to load");
        System.out.println("blacklist size is:" + blacklists.size());
        for (int i = 0; i < blacklists.size(); i++) {
            Map<String,Object> map = new HashMap<String,Object>();
            System.out.println("befor getuserid");
            int touserid = blacklists.get(i).getTouserid();
            System.out.println("geted touserid:"+touserid);
            user user = userDao.searchbyid(touserid);
            System.out.println("get user");
            String tousername = user.getUsername();
            map.put("touserid",touserid);
            map.put("tousername",tousername);
            blackinfolist.add(map);
        }
        System.out.println("ready to add model");
        model.addAttribute("blacklistinfo",blackinfolist);
        model.addAttribute("blacklistinonpage",blacklistinonepage);
        model.addAttribute("blacklistcount",blackinfolist.size());
        return "/blacklist";
    }
    @GetMapping("/delblacklist/{touserid}")
    public String delblacklist(@PathVariable("touserid")int touserid,HttpServletRequest request){
        int userid = (int)request.getSession().getAttribute("id");
        blacklistDao.delblacklist(userid,touserid);
        return "/blacklist";
    }
}
