package com.myblog.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class mywebmvcconfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/testtest").setViewName("test");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/signup").setViewName("signup");
        registry.addViewController("/main").setViewName("main");
        registry.addViewController("/home").setViewName("home");
        registry.addViewController("/myhome").setViewName("myhome");
        registry.addViewController("/directreply").setViewName("directreply");
        registry.addViewController("/editblog").setViewName("editblog");
        registry.addViewController("/editmsg").setViewName("editmsg");
        registry.addViewController("/editpassword").setViewName("editpassword");
        registry.addViewController("/reeditblog").setViewName("reeditblog");
        //registry.addViewController("/myphoto").setViewName("personalphoto");
        registry.addViewController("/myblog").setViewName("myblog");
        registry.addViewController("/blog").setViewName("blog");
        registry.addViewController("/chat").setViewName("chat");
        registry.addViewController("/chatbox").setViewName("chatbox");
        registry.addViewController("/blacklist").setViewName("blacklist");
        registry.addViewController("/personalpage").setViewName("personalpage");
        registry.addViewController("/admin/blogs").setViewName("admin/manageblog");
        registry.addViewController("/admin/bloglist").setViewName("admin/bloglist");
        registry.addViewController("/admin/users").setViewName("admin/manageusers");
        registry.addViewController("/admin/tags").setViewName("admin/tags");
        registry.addViewController("/admin/photos").setViewName("admin/photos");
        registry.addViewController("/admin/commens").setViewName("admin/commens");
        registry.addViewController("/admin/chats").setViewName("admin/chats");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
            registry.addResourceHandler("/images/**").addResourceLocations("file:D:/img/");

        /*registry.addResourceHandler("/images/**").addResourceLocations("D:\\code\\IDEACode\\myblog\\src\\main\\resources\\static\\img");
        *//*classpath:/static/img/*/
    }

    @Bean
    public adminintercepter adminintercepter(){
        return new adminintercepter();
    }
    @Bean
    public userintercepter userintercepter(){
        return new userintercepter();
    }
    @Bean
    public loginintercepter loginintercepter(){return new loginintercepter();}
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(adminintercepter()).addPathPatterns("/admin/**");
        registry.addInterceptor(userintercepter()).addPathPatterns(
                "/testtest","/editpassword","/main","/home","/myhome","/directreply","/editblog","/editmsg","/myphoto","/myblog","blog","/chat","/chatbox","/blacklist","/personalpage");
    }
}
