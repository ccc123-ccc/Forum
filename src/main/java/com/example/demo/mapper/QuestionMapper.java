package com.example.demo.mapper;

import com.example.demo.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface QuestionMapper {
    @Insert("insert into question (title,description,time_create,time_modify,creator,tag,avatar_url) values(#{title},#{description},#{time_create},#{time_modify},#{creator},#{tag},#{avatar_url})")
    void insert(Question question);
    @Select ("Select * from question limit #{offset},#{size}")
    List<Question> list(@Param ("offset") Integer offset,@Param ("size") Integer size );
//    @Select ("select * from question ")
//    List<Question> list();
    @Select ("select count(1) from question")
    Integer count();


}
