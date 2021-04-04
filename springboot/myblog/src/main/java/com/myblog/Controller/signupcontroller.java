package com.myblog.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Controller;

import javax.xml.ws.Action;
import java.util.Random;
import com.myblog.Dao.emailcheckDao;
import com.myblog.Dao.userDao;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class signupcontroller {

    @Autowired
    JavaMailSenderImpl javaMailSender;
    @Autowired
    emailcheckDao emailcheckDao;
    @Autowired
    userDao userDao;
    @PostMapping("/sendmail")
    @ResponseBody
    public int sendcode(@RequestParam("email") String email){
        System.out.println("entersendmail");
        String emailcode = getRandomString(6);
        System.out.println("get random code");
        emailcheckDao.setcode(email,emailcode);
        System.out.println("success setcode");
        sendaemail(email,emailcode);
        System.out.println("success sendmail");
        return 1;
    }
    @PostMapping("/tosignup")
    @ResponseBody
    public int checkcode(@RequestParam("password")String password,@RequestParam("username")String username,@RequestParam("email")String email,@RequestParam("checkcode")String checkcode){
        System.out.println("1");
        if(emailcheckDao.isoutdate(email, checkcode) == 2){
            System.out.println("2");
            userDao.adduser(username,password,email);
            return 2;
        }else {
            System.out.println("3");
            return emailcheckDao.isoutdate(email, checkcode);
        }

        //0-没查到
        //1-过期了
        //2-验证成功
    }
    @PostMapping("/checkname")
    @ResponseBody
    public int checkname(@RequestParam("username")String username){
        if(username.length()<4||username.length()>15) return 2;
        else if(userDao.searchbyname(username)==null) return 1;//无重名，可注册
        else return 0;//有重名
    }

    public void sendaemail(String toemail,String checkcode){
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setSubject("myblog验证码");
        simpleMailMessage.setText("五分钟内有效，您的验证码为:"+checkcode);
        simpleMailMessage.setTo(toemail);
        simpleMailMessage.setFrom("s_k_yy@163.com");
        javaMailSender.send(simpleMailMessage);
    }
    public static String getRandomString(int length){
        String str="abcdefghijklmnopqrstuvwxyz0123456789";
        Random random=new Random();
        StringBuffer sb=new StringBuffer();
        for(int i=0;i<length;i++){
            int number=random.nextInt(36);
            sb.append(str.charAt(number));
        }
        return sb.toString();
    }
}
