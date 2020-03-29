package com.bk.community.mapper;

import com.bk.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author bear
 */
@Mapper
@Repository
public interface QuestionMapper {

    /**
     * 问题列表
     */
    @Select("select * from co_question")
    List<Question> list();
}
