package com.bk.community.mapper;

import com.bk.community.model.Question;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author bear
 */
@Mapper
@Repository
public interface PublishMapper {

    /**
     * 发布问题
     * @param question 需要添加的问题
     */
    @Insert("insert into co_question(title,description,creator,tag,gmt_create,gmt_modified) values (#{title},#{description},#{creator},#{tag},#{gmtCreate},#{gmtModified})")
    void create(Question question);
}
