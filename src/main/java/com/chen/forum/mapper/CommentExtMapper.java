package com.chen.forum.mapper;

import com.chen.forum.model.Comment;
public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}