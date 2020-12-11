package com.example.demo.controller;

import com.example.demo.DTO.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
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
    private UserMapper userMapper;
    @Autowired
    private QuestionService questionService;

    @GetMapping("/")
    public String index (HttpServletRequest request,
                         Model model) {
        if (request.getCookies () != null) {
            Cookie[] cookies = request.getCookies ();
            String token = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName ().equals ("Token")) {
                    token = cookie.getValue ();
                    User user = userMapper.findByToken (token);
                    if (user != null) {
                        request.getSession ().setAttribute ("user", user);
                    }
                    break;
                }
            }
        }
        List<QuestionDTO> questionDTOList=questionService.list();
        model.addAttribute ("questionList",questionDTOList);
        return "index";
    }


}