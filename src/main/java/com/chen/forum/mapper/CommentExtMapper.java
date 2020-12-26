package com.chen.forum.mapper;

import com.chen.forum.model.Comment;
import com.chen.forum.model.CommentExample;
import com.chen.forum.model.Question;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}