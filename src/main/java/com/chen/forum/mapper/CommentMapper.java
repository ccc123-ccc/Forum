package com.chen.forum.mapper;

import com.chen.forum.model.Comment;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    @Insert("insert into comment (parent_id,type,commentator,gmt_create,gmt_modified,like_count,content) values(#{parent_id},#{type},#{commentator},#{gmt_create},#{gmt_modified},#{like_count},#{content})")
    void insert(Comment comment);
}
