package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

//@repository
@Mapper
public interface GithubUserMapper {
    @Insert("insert into user (name,account_id,token,time_create,avatar_url,time_modify) " +
            "values(#{name},#{accountId},#{token},#{time_create},#{avatar_url},#{time_modify})")
    void insert (User user);

    @Select("select * from user where token=#{token}")
    User findByToken (@Param("token") String token);

    @Select("select * from user where id=#{creator}")
    User findById (@Param("creator") Integer creator);

    @Select("select * from user where account_id=#{accountId}")
    User findByAccountId (@Param("accountId") String accountId);

    @Update("update user set name=#{name},time_create=#{time_create},time_modify=#{time_modify}," +
            "avatar_url=#{avatar_url},token=#{token} where account_id=#{accountId}")
    void update (User user);
}
