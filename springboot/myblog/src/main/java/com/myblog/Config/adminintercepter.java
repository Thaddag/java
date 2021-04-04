package com.myblog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class adminintercepter implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("level")==null){
            response.sendRedirect("/login");
            return false;
        }
        int level = (int)request.getSession().getAttribute("level");
        if(level == 1){
            return true;
        }else {
            response.sendRedirect("/login");
            return false;
        }
    }
}
