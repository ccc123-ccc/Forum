package com.example.demo.mapper;

import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

public interface QuestionExtMapper {
    int updateView (Question record);

    int updateCommentCount (Question record);

    List<Question> selectRelated (Question question);
}