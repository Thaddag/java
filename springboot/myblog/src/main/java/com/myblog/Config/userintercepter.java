package com.myblog.Config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class userintercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("level")==null){
            response.sendRedirect("/login");
            return false;
        }
        int level = (int)request.getSession().getAttribute("level");
        if(level == 0){
            return true;
        }else {
            response.sendRedirect("/login");
            return false;
        }
    }
}
