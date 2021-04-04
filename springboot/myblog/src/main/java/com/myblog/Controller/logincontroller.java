package com.myblog.Controller;

import com.myblog.Dao.userDao;
import com.myblog.pojo.user;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class logincontroller {
    @Autowired
    userDao userdao;
    @PostMapping("/tologin")
    @ResponseBody
    public int login(HttpSession session,HttpServletRequest request, Model model, @RequestParam("name") String username, @RequestParam("password") String password){

        if(userdao.searchbyname(username) == null) {
            //request.setAttribute("msg","用户名密码错误");
            model.addAttribute("msg","用户名密码错误");
            return 0;
        }

        user user = userdao.searchbyname(username);
        if(user.getUserpassword().equals(password)){
            request.getSession().setAttribute("id", user.getId());
            request.getSession().setAttribute("headimg", user.getImg());
            request.getSession().setAttribute("name",user.getUsername());
            request.getSession().setAttribute("email",user.getEmail());

            if(user.getEnable() == 0) {
                System.out.println("该用户已被注销，无法登录");
                model.addAttribute("msg","该用户已被注销，无法登录");
                return -1;
            }else if(user.getLevel() == 1){
                System.out.println(user.getLevel());
                request.getSession().setAttribute("level",1);
                return 1;//管理员
            }else {
                request.getSession().setAttribute("level",0);
                return 2;//用户
            }
        }else {
            System.out.println("passwordfail");
            model.addAttribute("msg","用户名密码错误");

            return 0;//不给登
        }
    }
    @GetMapping("/")
    public String index(){
        return "/login";
    }
}
