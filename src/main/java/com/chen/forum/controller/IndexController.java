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
                        @RequestParam(name="size",defaultValue = "5") Integer size,
                        @RequestParam(name="search",required = false) String search){
        PageDTO questionList= questionService.list(search,page,size);
        model.addAttribute("pagination",questionList);
        model.addAttribute("search",search);
        return "index";
    }
}
