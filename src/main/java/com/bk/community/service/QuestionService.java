package com.bk.community.service;

import com.bk.community.dto.PaginationDTO;
import com.bk.community.dto.QuestionDTO;
import com.bk.community.exception.CustomizeErrorCode;
import com.bk.community.exception.CustomizeException;
import com.bk.community.mapper.QuestionExtMapper;
import com.bk.community.mapper.QuestionMapper;
import com.bk.community.mapper.UserMapper;
import com.bk.community.model.Question;
import com.bk.community.model.QuestionExample;
import com.bk.community.model.User;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author bear
 */
@Service
public class QuestionService {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionExtMapper questionExtMapper;

    public void createOrUpdate(Question question) {
        if (question.getId() == null) {
            // 新增
            question.setGmtCreate(System.currentTimeMillis());
            question.setGmtModified(question.getGmtCreate());
            questionMapper.insert(question);
        } else {
            // 更新
            Question updateQuestion = new Question();
            updateQuestion.setGmtModified(System.currentTimeMillis());
            updateQuestion.setTitle(question.getTitle());
            updateQuestion.setDescription(question.getDescription());
            updateQuestion.setTag(question.getTag());
            QuestionExample questionExample = new QuestionExample();
            questionExample.createCriteria().andIdEqualTo(question.getId());
            int updated = questionMapper.updateByExampleSelective(updateQuestion, questionExample);
            if (updated != 1) {
                throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
            }
        }
    }

    public PaginationDTO<QuestionDTO> list(Integer page, Integer size) {
        PaginationDTO<QuestionDTO> questionDtoPagination = new PaginationDTO<>();
        Integer count = (int) questionMapper.countByExample(new QuestionExample());
        questionDtoPagination.setTotalCount(count);
        questionDtoPagination.setPagination(count, page, size);

        // 获取处理后的page值
        page = questionDtoPagination.getCurrentPage();

        List<Question> questions = questionMapper.selectByExampleWithRowbounds(new QuestionExample(), new RowBounds(page, size));
        List<QuestionDTO> questionDtoList = new ArrayList<>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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

        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria().andCreatorEqualTo(uid);
        Integer count = (int) questionMapper.countByExample(questionExample);
        questionDtoPagination.setTotalCount(count);
        questionDtoPagination.setPagination(count, page, size);

        // 获取处理后的page值
        page = questionDtoPagination.getCurrentPage();


        QuestionExample example = new QuestionExample();
        example.createCriteria().andCreatorEqualTo(uid);
        List<Question> questions = questionMapper.selectByExampleWithRowbounds(example, new RowBounds(page, size));

        List<QuestionDTO> questionDtoList = new ArrayList<QuestionDTO>();
        for (Question question : questions) {
            User user = userMapper.selectByPrimaryKey(question.getCreator());
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
        Question question = questionMapper.selectByPrimaryKey(id);
        if (Objects.isNull(question)) {
            throw new CustomizeException(CustomizeErrorCode.QUESTION_NOT_FOUND);
        }
        User user = userMapper.selectByPrimaryKey(question.getCreator());
        BeanUtils.copyProperties(question, questionDTO);
        questionDTO.setUser(user);
        return questionDTO;
    }

    public Integer incView(Integer id) {
        Question record = new Question();
        record.setId(id);
        record.setViewCount(1);
        int i = questionExtMapper.incView(record);
        return i;
    }
}
