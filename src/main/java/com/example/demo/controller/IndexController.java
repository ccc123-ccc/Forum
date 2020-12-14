package com.example.demo.controller;

import com.example.demo.DTO.PagesDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.mapper.GithubUserMapper;
import com.example.demo.model.User;
import com.example.demo.service.QuestionService;
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
    private GithubUserMapper githubUserMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index (HttpServletRequest request,
                         Model model,
                         @RequestParam(name = "page", defaultValue = "1") Integer page,
                         @RequestParam(name = "size", defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies ();
        if (cookies != null && cookies.length != 0) {
            String token = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName ().equals ("Token")) {
                    token = cookie.getValue ();
                    User user = githubUserMapper.findByToken (token);
                    if (user != null) {
                        request.getSession ().setAttribute ("user", user);
                    }
                    break;
                }
            }
        }
        PagesDTO pagesDTO = questionService.list (page, size);
        model.addAttribute ("pagesList", pagesDTO);
        return "index";
    }


}