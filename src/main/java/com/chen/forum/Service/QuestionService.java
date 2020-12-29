package com.chen.forum.Service;

import com.chen.forum.DTO.PageDTO;
import com.chen.forum.DTO.QuestionDTO;
import com.chen.forum.DTO.QuestionQueryDTO;
import com.chen.forum.exception.CustomizeErrorCode;
import com.chen.forum.exception.CustomizeException;
import com.chen.forum.mapper.QuestionExtMapper;
import com.chen.forum.mapper.QuestionMapper;
import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.Question;
import com.chen.forum.model.QuestionExample;
import com.chen.forum.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QuestionService {

    @Autowired
    private UserMapper userMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    public PageDTO list(long userId, Integer page, Integer size) {
        Integer offset=size*(page-1);
        QuestionExample example1 = new QuestionExample();
        example1.createCriteria().andCreatorEqualTo(userId);
        List<Question> list = questionMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        ArrayList<QuestionDTO> questionDTOArrayList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question question:list){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOArrayList.add(questionDTO);
        }
        pageDTO.setData(questionDTOArrayList);
        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(userId);
        Integer totalcount = (int)questionMapper.countByExample(example);
        pageDTO.setPageDTO(totalcount,page,size);
        return pageDTO;
    }

    public PageDTO list(String search,Integer page, Integer size) {
        Integer offset=size*(page-1);
        String replacesearch=null;
        if(search!=null){
            replacesearch = search.replace(" ", "|").replace(",", "|");
        }
        QuestionQueryDTO questionQueryDTO = new QuestionQueryDTO();
        questionQueryDTO.setSearch(replacesearch);
        questionQueryDTO.setOffset(offset);
        questionQueryDTO.setSize(size);
        List<Question> list = questionExtMapper.selectBySearch(questionQueryDTO);
        ArrayList<QuestionDTO> questionDTOArrayList = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Question question:list){
            User user=userMapper.selectByPrimaryKey(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question,questionDTO);
            questionDTO.setUser(user);
            questionDTOArrayList.add(questionDTO);
        }
        pageDTO.setData(questionDTOArrayList);
        Integer totalcount=questionExtMapper.countBySearch(questionQueryDTO);
        pageDTO.setPageDTO(totalcount,page,size);
        return pageDTO;
    }

    public QuestionDTO getById(long id) {
        Question question=questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question,questionDTO);
        User user=userMapper.selectByPrimaryKey(question.getCreator());
        questionDTO.setUser(user);
        return questionDTO;
    }

    public void createOrUpdate(Question question) {
        //插入
        if(question.getId()==null){
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            question.setCommentCount(0);
            question.setViewCount(0);
            question.setLikeCount(0);
            questionMapper.insert(question);
        }
        //更新
        else{
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample example = new QuestionExample();
            example.createCriteria()
                    .andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, example);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public void incView(long id) {
        Question question = new Question();
        question.setId(id);
        question.setViewCount(1);
        questionExtMapper.incView(question);
    }

    public List<QuestionDTO> selectRelated(QuestionDTO queryDTO) {
        if(StringUtils.isBlank(queryDTO.getTag())){
            return new ArrayList<>();
        }
        String replacetag = StringUtils.replace(queryDTO.getTag(), ",", "|");
        Question question = new Question();
        question.setId(queryDTO.getId());
        question.setTag(replacetag);
        List<Question> questions = questionExtMapper.selectRelated(question);
        List<QuestionDTO> questionDTOS = questions.stream().map(q -> {
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(q, questionDTO);
            return questionDTO;
        }).collect(Collectors.toList());
        return questionDTOS;
    }
}
