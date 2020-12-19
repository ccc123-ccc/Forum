package com.chen.forum.Service;

import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.User;
import com.chen.forum.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andAccountIdEqualTo(user.getAccountId());
        List<User> users = userMapper.selectByExample(example);
        User dbuser = users.get(0);
        if(dbuser==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else{
            User user1=new User();
            user1.setName(user.getName());
            user1.setToken(user.getToken());
            user1.setAvatarUrl(user.getAvatarUrl());
            user1.setGmtModified(System.currentTimeMillis());
            UserExample userExample = new UserExample();
            userExample.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(user1,userExample);
        }
    }
}
