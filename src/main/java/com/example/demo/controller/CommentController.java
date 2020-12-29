package com.example.demo.controller;

import com.example.demo.DTO.CommentCreateDTO;
import com.example.demo.DTO.CommentDTO;
import com.example.demo.DTO.ResultDTO;
import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.model.Comment;
import com.example.demo.model.User;
import com.example.demo.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    CommentService commentService;
    @ResponseBody
    @RequestMapping(value = "/comment",method = RequestMethod.POST)
    public Object post (@RequestBody CommentCreateDTO commentCreateDTO,
                        HttpServletRequest request){
        User user=(User)request.getSession ().getAttribute ("user");
        if(user==null){
            return ResultDTO.errorOf (CustomizeErrorCode.NO_LOG_IN);
        }
        if(commentCreateDTO.getContent ()==null||commentCreateDTO.getContent ().length ()==0){
            return ResultDTO.errorOf (CustomizeErrorCode.CONTENT_EMPTY);
        }
        Comment comment=new Comment ();
        comment.setContent (commentCreateDTO.getContent ());
        comment.setParentId (commentCreateDTO.getParentId ());
        comment.setTimeCreate (System.currentTimeMillis ());
        comment.setTimeModify (System.currentTimeMillis ());
        comment.setType (commentCreateDTO.getType ());
        comment.setCommentator (user.getId ());
        comment.setLikeCount (0L);
        comment.setCommentCount (0);
        commentService.insert(comment);
        return ResultDTO.okOf ();
    }
    @ResponseBody
    @RequestMapping(value = "/comment/{id}",method = RequestMethod.GET)
    public ResultDTO<List<CommentDTO>> comments(@PathVariable(name="id") Integer id){
        List<CommentDTO> subCommentDTOList = commentService.listByTargetId (id, CommentTypeEnums.COMMENT);
        return ResultDTO.okOf (subCommentDTOList);
    }
}
