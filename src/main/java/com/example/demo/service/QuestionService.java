package com.example.demo.service;

import com.example.demo.DTO.PagesDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.QuestionExample;
import com.example.demo.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public QuestionDTO getById (Integer id) {
        QuestionExample example = new QuestionExample ();
        example.createCriteria ()
                .andIdEqualTo (id);
        List<Question> questions = questionMapper.selectByExample (example);
        Question question = questions.get (0);
        QuestionDTO questionDTO = new QuestionDTO ();
        User user = userMapper.selectByPrimaryKey (Integer.valueOf (question.getCreator ()));
        BeanUtils.copyProperties (question, questionDTO);
        questionDTO.setUser (user);
        return questionDTO;
    }

    public PagesDTO list (Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.selectByExampleWithRowbounds (new QuestionExample (), new RowBounds (offset, size));
//        List<Question> questions = questionMapper.list (offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<> ();
        PagesDTO pagesDTO = new PagesDTO ();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey (Integer.valueOf (question.getCreator ()));
            QuestionDTO questionDTO = new QuestionDTO ();
            BeanUtils.copyProperties (question, questionDTO);
            questionDTO.setUser (user);
            questionDTOList.add (questionDTO);
        }
        pagesDTO.setQuestionList (questionDTOList);
        Integer count = (int) questionMapper.countByExample (new QuestionExample ());
        pagesDTO.setPages (page, size, count);
        return pagesDTO;
    }

    public PagesDTO list (Integer id, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        QuestionExample example1 = new QuestionExample ();
        example1.createCriteria ()
                .andIdEqualTo (id);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds (example1, new RowBounds (offset, size));
//        List<Question> questions = questionMapper.listById (id,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<> ();
        PagesDTO pagesDTO = new PagesDTO ();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey (Integer.valueOf (question.getCreator ()));
            QuestionDTO questionDTO = new QuestionDTO ();
            BeanUtils.copyProperties (question, questionDTO);
            questionDTO.setUser (user);
            questionDTOList.add (questionDTO);
        }
        pagesDTO.setQuestionList (questionDTOList);
//        Integer count=questionMapper.countById (id);
        QuestionExample example = new QuestionExample ();
        example.createCriteria ()
                .andIdEqualTo (id);
        Integer count = (int) questionMapper.countByExample (example);
        pagesDTO.setPages (page, size, count);
        return pagesDTO;
    }

    public void createOrUpdate (Question question) {
        if (question.getId () == null) {
            question.setTimeModify (question.getTimeCreate ());
            questionMapper.insert (question);
        } else {
            Question updateQuestion = new Question ();
            updateQuestion.setTimeModify (System.currentTimeMillis ());
            updateQuestion.setDescription (question.getDescription ());
            updateQuestion.setTitle (question.getTitle ());
            updateQuestion.setTag (question.getTag ());
            updateQuestion.setId (question.getId ());
            QuestionExample example = new QuestionExample ();
            example.createCriteria ()
                    .andIdEqualTo (question.getId ());
            questionMapper.updateByExampleSelective (updateQuestion, example);
        }

    }

//    public void updateViewCount (Integer id) {
//        questionMapper.(id);
//    }
}
