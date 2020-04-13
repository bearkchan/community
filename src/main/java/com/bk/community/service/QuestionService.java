package com.bk.community.service;

import com.bk.community.dto.PaginationDTO;
import com.bk.community.dto.QuestionDTO;
import com.bk.community.mapper.QuestionMapper;
import com.bk.community.mapper.UserMapper;
import com.bk.community.model.Question;
import com.bk.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author bear
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    public void createOrUpdate(Question question) {
        if (question.getId()==null){
            // 新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.create(question);
        }else {
            // 更新
            question.setGmtModified(System.currentTimeMillis());
            questionMapper.update(question);
        }
    }

    public PaginationDTO<QuestionDTO> list(Integer page, Integer size) {
        PaginationDTO<QuestionDTO> questionDtoPagination = new PaginationDTO<>();
        Integer count = questionMapper.count();
        questionDtoPagination.setTotalCount(count);
        questionDtoPagination.setPagination(count, page, size);

        // 获取处理后的page值
        page = questionDtoPagination.getCurrentPage();

        List<Question> questions = questionMapper.list(page, size);
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questions) {
            User user = userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDtoList.add(questionDTO);
        }

        questionDtoPagination.setData(questionDtoList);
        return questionDtoPagination;
    }

    public PaginationDTO<QuestionDTO> getListByUser(Integer uid, Integer page, Integer size) {
        PaginationDTO<QuestionDTO> questionDtoPagination = new PaginationDTO<>();
        Integer count = questionMapper.countByUser(uid);
        questionDtoPagination.setTotalCount(count);
        questionDtoPagination.setPagination(count, page, size);

        // 获取处理后的page值
        page = questionDtoPagination.getCurrentPage();

        List<Question> questions = questionMapper.getListByUser(uid, page, size);
        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questions) {
            User user = userMapper.findUserById(question.getCreator());
            QuestionDTO questionDTO = new QuestionDTO();
            BeanUtils.copyProperties(question, questionDTO);
            questionDTO.setUser(user);
            questionDtoList.add(questionDTO);
        }

        questionDtoPagination.setData(questionDtoList);
        return questionDtoPagination;
    }

    public QuestionDTO detail(Integer id) {
        QuestionDTO questionDTO = new QuestionDTO();
        Question question = questionMapper.getQuestionById(id);
        User user = userMapper.findUserById(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }
}
