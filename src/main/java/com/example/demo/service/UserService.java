package com.example.demo.service;

import com.example.demo.mapper.GithubUserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private GithubUserMapper githubUserMapper;
    public void createOrUpdate (User user) {
        User dbUser=githubUserMapper.findByAccountId(user.getAccountId ());
        if(dbUser==null){
            user.setTime_create (System.currentTimeMillis ());
            user.setTime_modify (user.getTime_create ());
            githubUserMapper.insert (user);
        }else{
            dbUser.setTime_create (System.currentTimeMillis ());
            dbUser.setAccountId (user.getAccountId ());
            dbUser.setTime_modify (user.getTime_create ());
            dbUser.setAvatar_url (user.getAvatar_url ());
            dbUser.setName (user.getName ());
            dbUser.setToken (user.getToken ());
            githubUserMapper.update(dbUser);
        }
    }
}
