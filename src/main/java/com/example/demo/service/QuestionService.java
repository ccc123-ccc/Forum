package com.example.demo.service;

import com.example.demo.DTO.PagesDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.GithubUserMapper;
import com.example.demo.model.Question;
import com.example.demo.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private GithubUserMapper githubUserMapper;
    @Autowired
    private QuestionMapper questionMapper;

    public  QuestionDTO getById (Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO ();
        User user = githubUserMapper.findById (question.getCreator ());
        BeanUtils.copyProperties (question, questionDTO);
        questionDTO.setUser (user);
        return questionDTO;
    }

    public PagesDTO list (Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.list (offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<> ();
        PagesDTO pagesDTO = new PagesDTO ();
        for (Question question : questions) {
            User user = githubUserMapper.findById (question.getCreator ());
            QuestionDTO questionDTO = new QuestionDTO ();
            BeanUtils.copyProperties (question, questionDTO);
            questionDTO.setUser (user);
            questionDTOList.add (questionDTO);
        }
        pagesDTO.setQuestionList (questionDTOList);
        Integer count=questionMapper.count ();
        pagesDTO.setPages(page,size,count);
        return pagesDTO;
    }

    public PagesDTO list (Integer id, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        List<Question> questions = questionMapper.listById (id,offset, size);
        List<QuestionDTO> questionDTOList = new ArrayList<> ();
        PagesDTO pagesDTO = new PagesDTO ();
        for (Question question : questions) {
            User user = githubUserMapper.findById (question.getCreator ());
            QuestionDTO questionDTO = new QuestionDTO ();
            BeanUtils.copyProperties (question, questionDTO);
            questionDTO.setUser (user);
            questionDTOList.add (questionDTO);
        }
        pagesDTO.setQuestionList (questionDTOList);
        Integer count=questionMapper.countById (id);
        pagesDTO.setPages(page,size,count);
        return pagesDTO;
    }
}
