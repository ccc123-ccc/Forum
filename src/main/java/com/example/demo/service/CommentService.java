package com.example.demo.service;

import com.example.demo.DTO.CommentDTO;
import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.*;
import com.example.demo.model.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    CommentExtMapper commentExtMapper;

    @Transactional
    public void insert (Comment comment) {
        if (comment.getParentId () == null || comment.getParentId () == 0) {
            throw new CustomizeException (CustomizeErrorCode.TARGET_PARENT_NOT_FOUND);
        }
        if (comment.getType () == null || !CommentTypeEnums.isExist (comment.getType ())) {
            throw new CustomizeException (CustomizeErrorCode.TYPE_PARAM_ERROR);
        }
        if (comment.getType () == CommentTypeEnums.COMMENT.getType ()) {
            Comment dbComment = commentMapper.selectByPrimaryKey (comment.getParentId ());
            if (dbComment == null) {
                throw new CustomizeException (CustomizeErrorCode.COMMENT_PARAM_ERROR);
            }
            commentMapper.insert (comment);
            Comment comment1 = new Comment ();
            comment1.setId (comment.getParentId ());
            comment1.setCommentCount (1);
            commentExtMapper.updateCommentCount (comment1);
        } else {
            Question question = questionMapper.selectByPrimaryKey (comment.getParentId ());
            if (question == null) {
                throw new CustomizeException (CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
            commentMapper.insert (comment);
            question.setCommentCount (1);
            questionExtMapper.updateCommentCount (question);
        }


    }

    public List<CommentDTO> listByTargetId (Integer id, CommentTypeEnums question) {
        CommentExample commentExample = new CommentExample ();
        commentExample.createCriteria ()
                .andParentIdEqualTo (id)
                .andTypeEqualTo (question.getType ());
        commentExample.setOrderByClause ("time_create desc");
        List<Comment> comments = commentMapper.selectByExample (commentExample);
        if (comments.size () == 0) {
            return new ArrayList<> ();
        }

        //获取不重复的评论人
        Set<Integer> commentators = comments.stream ().map (comment -> comment.getCommentator ()).collect (Collectors.toSet ());
        List<Integer> userId = new ArrayList<> ();
        userId.addAll (commentators);

        //获取评论人转化为map
        UserExample userExample = new UserExample ();
        userExample.createCriteria ()
                .andIdIn (userId);
        List<User> users = userMapper.selectByExample (userExample);
        Map<Integer, User> userMap = users.stream ().collect (Collectors.toMap (user -> user.getId (), user -> user));

        //讲comment转化为commentDTO
        List<CommentDTO> commentDTOs = comments.stream ().map (comment -> {
            CommentDTO commentDTO = new CommentDTO ();
            BeanUtils.copyProperties (comment, commentDTO);
            commentDTO.setUser (userMap.get (comment.getCommentator ()));
            return commentDTO;
        }).collect (Collectors.toList ());
        return commentDTOs;
    }
}
