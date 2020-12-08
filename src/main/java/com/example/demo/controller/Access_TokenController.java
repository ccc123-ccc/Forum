package com.example.demo.controller;

import com.example.demo.DTO.AccessTokenDTO;
import com.example.demo.DTO.GithubUserDTO;
import com.example.demo.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Access_TokenController {
    @Autowired
    GithubProvider githubProvider;

    @GetMapping( "/callback")
    public String callback(@RequestParam(name="code") String code,
                            @RequestParam(name="state")String state){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO ();
        accessTokenDTO.setCode (code);
        accessTokenDTO.setClient_id ("3783cac01f9c1383eb5c");
        accessTokenDTO.setState (state);
        accessTokenDTO.setClient_secret ("5d30917ba0aad4b50106645d30f6c45ea6bea49a");
        accessTokenDTO.setRedirect_uri ("http://localhost:8080/callback");
        String Access_token=githubProvider.getAccessToken (accessTokenDTO);
        GithubUserDTO githubUserDTO=githubProvider.getUser (Access_token);

        System.out.println (githubUserDTO.getName ());
        return "index";
    }

}
