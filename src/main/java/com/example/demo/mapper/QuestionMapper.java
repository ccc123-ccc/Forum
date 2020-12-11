package com.example.demo.mapper;

import com.example.demo.DTO.Question;
import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,time_create,time_modify,creator,tag,avatar_url) values(#{title},#{description},#{time_create},#{time_modify},#{creator},#{tag},#{avatar_url})")
    void insert(Question question);
}
