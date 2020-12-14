package com.example.demo.mapper;

import com.example.demo.model.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;

//@repository
@Mapper
public interface GithubUserMapper {
    @Insert("insert into user (name,account_id,token,time_create,avatar_url,time_modify) values(#{name},#{accountId},#{token},#{time_create},#{avatar_url},#{time_modify})")
//    @Insert("insert into user (name,account_id,token,time_create,time_modify) values (#{name},#{accountId},#{token},#{time_create},#{time_modify}")
    void insert(User user);

    @Select("select * from user where token=#{token}")
    User findByToken (@Param ("token") String token);
    @Select("select * from user where id=#{creator}")
    User findById (@Param ("creator")Integer creator);
}
