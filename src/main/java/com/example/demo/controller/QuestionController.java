package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import com.example.demo.service.CommentService;
import com.example.demo.service.NotificationService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    NotificationService notificationService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model,
                           HttpServletRequest request){
        QuestionDTO questionDTO= questionService.getById(id);
        List<CommentDTO> commentDTOList=commentService.listByTargetId (id, CommentTypeEnums.QUESTION);
        List<QuestionDTO> relatedQuestionDTOS=questionService.listByRegexp(questionDTO);
        questionService.updateViewCount (id);
        Cookie[] cookies = request.getCookies ();
        if (cookies != null && cookies.length != 0) {
            String token = null;
            for (Cookie cookie : cookies) {
                if (cookie.getName ().equals ("Token")) {
                    token = cookie.getValue ();
                    UserExample userExample = new UserExample ();
                    userExample.createCriteria ()
                            .andTokenEqualTo (token);
                    List<User> users = userMapper.selectByExample (userExample);
                    if (users.size ()!=0) {
                        request.getSession ().setAttribute ("user", users.get (0));
                        Integer unReadCount= notificationService.getUnreadCount(users.get (0));
                        request.getSession ().setAttribute ("unReadCount",unReadCount);
                    }
                    break;
                }
            }
        }
        model.addAttribute ("question",questionDTO);
        model.addAttribute ("comments",commentDTOList);
        model.addAttribute ("relatedQuestions",relatedQuestionDTOS);
        return "question";
    }
}
