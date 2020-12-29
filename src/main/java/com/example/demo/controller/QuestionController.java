package com.example.demo.controller;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.service.CommentService;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @Autowired
    CommentService commentService;
    @GetMapping("/question/{id}")
    public String question(@PathVariable(name="id") Integer id,
                           Model model){
        QuestionDTO questionDTO= questionService.getById(id);
        List<CommentDTO> commentDTOList=commentService.listByTargetId (id, CommentTypeEnums.QUESTION);
        questionService.updateViewCount (id);
        model.addAttribute ("question",questionDTO);
        model.addAttribute ("comments",commentDTOList);
        return "question";
    }
}
