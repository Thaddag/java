package com.myblog.Config;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class loginintercepter implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if(request.getSession().getAttribute("id")==null){
            /*RequestDispatcher rd = request.getRequestDispatcher("/login");
            rd.forward(request, response);*/
            response.sendRedirect("/login");
            return false;
        }else return true;
        /*int level = (int)request.getSession().getAttribute("level");
        if(level == 0){
            return true;
        }else return false;*/
    }
}
