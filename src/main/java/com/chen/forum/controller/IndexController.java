package com.chen.forum.controller;

import com.chen.forum.DTO.PageDTO;
import com.chen.forum.DTO.QuestionDTO;
import com.chen.forum.Service.QuestionService;
import com.chen.forum.mapper.QuestionMapper;
import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.Question;
import com.chen.forum.model.User;
import com.chen.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index(HttpServletRequest request,
                            Model model,
                        @RequestParam(name="page",defaultValue ="1" ) Integer page,
                        @RequestParam(name="size",defaultValue = "2") Integer size){
        Cookie[] cookies = request.getCookies();
        if(cookies!=null&&cookies.length!=0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    UserExample example = new UserExample();
                    example.createCriteria().andTokenEqualTo(token);
                    List<User> users = userMapper.selectByExample(example);
                    if (users.size()!=0) {
                        request.getSession().setAttribute("user", users.get(0));
                    }
                    break;
                }
            }
        }
        PageDTO questionList= questionService.list(page,size);
        model.addAttribute("pagination",questionList);
        return "index";
    }
}
