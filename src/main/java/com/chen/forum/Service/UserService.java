package com.chen.forum.Service;

import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    public void createOrUpdate(User user) {
        User dbuser=userMapper.findByAccountId(user.getAccountId());
        if(dbuser==null){
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(user.getGmtCreate());
            userMapper.insert(user);
        }
        else{
            dbuser.setName(user.getName());
            dbuser.setToken(user.getToken());
            dbuser.setAvatarurl(user.getAvatarurl());
            dbuser.setGmtModified(System.currentTimeMillis());
            userMapper.update(dbuser);
        }
    }
}
