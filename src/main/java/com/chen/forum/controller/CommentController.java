package com.chen.forum.controller;

import com.chen.forum.DTO.CommentDTO;
import com.chen.forum.mapper.CommentMapper;
import com.chen.forum.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class CommentController {
    @Autowired
    private CommentMapper commentMapper;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO){
        Comment comment = new Comment();
        comment.setCommentator(1);
        comment.setParent_id(commentDTO.getParent_id());
        comment.setType(commentDTO.getType());
        comment.setGmt_modified(System.currentTimeMillis());
        comment.setGmt_create(System.currentTimeMillis());
        comment.setContent(commentDTO.getContent());
        comment.setLike_count(0);
        commentMapper.insert(comment);
        HashMap<Object, Object> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put("messgae","success");
        return null;
    }
}
