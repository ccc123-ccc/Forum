package com.chen.forum.controller;

import com.chen.forum.DTO.PageDTO;
import com.chen.forum.Service.NotificationService;
import com.chen.forum.Service.QuestionService;
import com.chen.forum.mapper.QuestionMapper;
import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.User;
import com.chen.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProfileController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;
    @Autowired
    private NotificationService notificationService;
    @GetMapping("/profile/{action}")
    public String profile(HttpServletRequest request,
                          @PathVariable(name="action")String action,
                          Model model,
                          @RequestParam(name="page",defaultValue ="1" ) Integer page,
                          @RequestParam(name="size",defaultValue = "2") Integer size){
        User user= (User) request.getSession().getAttribute("user");
        if(user==null){
            return "redirect:/";
        }
        if("questions".equals(action)){
            model.addAttribute("section","questions");
            model.addAttribute("sectionName","我的提问");
            PageDTO pageDTO=questionService.list(user.getId(),page,size);
            model.addAttribute("pagination",pageDTO);
        }
        if("replies".equals(action)){
            model.addAttribute("section","replies");
            model.addAttribute("sectionName","最新回复");
            PageDTO pageDTO=notificationService.list(user.getId(),page,size);
            model.addAttribute("pagination",pageDTO);
//            Long noreadCount=notificationService.unreadCount(user.getId());
//            model.addAttribute("noreadCount",noreadCount);
        }

        return "profile";
    }
}
