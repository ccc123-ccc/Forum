package com.example.demo.service;

import com.example.demo.enums.CommentTypeEnums;
import com.example.demo.exception.CustomizeErrorCode;
import com.example.demo.exception.CustomizeException;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.mapper.QuestionExtMapper;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.model.Comment;
import com.example.demo.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    CommentMapper commentMapper;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    QuestionExtMapper questionExtMapper;

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
}
