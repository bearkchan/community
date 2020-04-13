package com.bk.community.mapper;

import com.bk.community.dto.PaginationDTO;
import com.bk.community.model.Question;
import org.apache.ibatis.annotations.*;
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
     *
     * @param page 页数
     * @param size 每页个数
     * @return
     */
    @Select("select * from co_question  order by gmt_create desc limit ${(page-1)*size},#{size}")
    List<Question> list(@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 获取总数
     *
     * @return
     */
    @Select("select count(1) from co_question")
    Integer count();

    /**
     *  获取我的问题列表
     * @param uid 用户id
     * @param page 页数
     * @param size 每页个数
     * @return
     */
    @Select("select * from co_question where creator=#{uid} limit ${(page-1)*size},#{size}")
    List<Question> getListByUser(@Param("uid") Integer uid,@Param("page") Integer page, @Param("size") Integer size);

    /**
     * 根据用户id获取总问题个数
     * @param uid  用户id
     * @return
     */
    @Select("select count(1) from co_question where creator=#{uid}")
    Integer countByUser(@Param("uid")Integer uid);


    /**
     * 根据问题id获取问题详情
     * @param id 问题id
     * @return
     */
    @Select("select * from co_question where id=#{id}")
    Question getQuestionById(@Param("id") Integer id);


    /**
     * 发布问题
     * @param question 需要添加的问题
     */
    @Insert("insert into co_question(title,description,creator,tag,gmt_create,gmt_modified) values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);

    @Update("update co_question set title=#{title}, description=#{description},tag=#{tag},gmt_modified=#{gmtModified} where id=#{id}")
    void update(Question question);
}
