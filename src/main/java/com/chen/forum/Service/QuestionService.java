package com.chen.forum.Service;

import com.chen.forum.DTO.PageDTO;
import com.chen.forum.DTO.QuestionDTO;
import com.chen.forum.mapper.QuestionMapper;
import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.Question;
import com.chen.forum.model.User;
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

    public PageDTO list(Integer userId, Integer page, Integer size) {
        Integer offset=size*(page-1);
        List<Question> list = questionMapper.listByUserId(userId,offset,size);
        ArrayList<QuestionDTO> questionDTOArrayList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question question:list){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOArrayList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOArrayList);
        Integer totalcount=questionMapper.countByUserId(userId);
        pageDTO.setPageDTO(totalcount,page,size);
        return pageDTO;
    }

    public PageDTO list(Integer page, Integer size) {
        Integer offset=size*(page-1);
        List<Question> list = questionMapper.list(offset,size);
        ArrayList<QuestionDTO> questionDTOArrayList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question question:list){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOArrayList.add(questionDTO);
        }
        pageDTO.setQuestions(questionDTOArrayList);
        Integer totalcount=questionMapper.count();
        pageDTO.setPageDTO(totalcount,page,size);
        return pageDTO;
    }

    public QuestionDTO getById(Integer id) {
        Question question=questionMapper.getById(id);
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }
        else{
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }
}
