package com.bk.community.mapper;

import com.bk.community.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

/**
 * @author bear
 */
@Mapper
@Repository
public interface UserMapper {

    /**
     * 新增用户
     *
     * @param user 所需插入的数据
     */
    @Insert("insert into co_user (account_id,name,token,gmt_create,gmt_modified,avatar_url,bio) values (#{accountId}, #{name}, #{token}, #{gmtCreate}, #{gmtModified},#{avatarUrl},#{bio})")
    void insert(User user);

    /**
     * 根据token值获取用户数据
     *
     * @param token token值
     * @return 用户
     */
    @Select("select * from co_user where token=#{token} limit 1")
    User findUserByToken(@Param("token") String token);

    /**
     * @param creator
     * @return
     */
    @Select("select * from co_user where id=#{creator} limit 1")
    User findUserById(@Param("creator") Integer creator);

    @Select("select * from co_user where account_id=#{id} limit 1")
    User findUserByAccountId(@Param("id") String accountId);


    @Update("update co_user set name=#{name}, token=#{token}, gmt_modified=#{gmtModified},avatar_url=#{avatarUrl},bio=#{bio} where id=#{id}")
    void update(User user);
}
