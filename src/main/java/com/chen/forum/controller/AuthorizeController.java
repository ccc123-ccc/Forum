package com.chen.forum.controller;

import com.chen.forum.DTO.AccessTokenDTO;
import com.chen.forum.DTO.GitHubUser;
import com.chen.forum.Provider.GitHubProvider;
import com.chen.forum.Service.UserService;
import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.User;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class AuthorizeController {
    @Autowired
    GitHubProvider gitHubProvider;
    @Value("${github.client.id}")
    private String clientid;
    @Value("${github.client.secret}")
    private String clientsercet;
    @Value("${github.redirect.uri}")
    private String uri;
    @Autowired
    private UserService userService;
    @GetMapping("/callback")
    public String callback(@RequestParam(name="code") String code,
                           @RequestParam(name="state") String state,
                           HttpServletRequest request,
                           HttpServletResponse response){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(clientid);
        accessTokenDTO.setClient_secret(clientsercet);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setRedirect_uri(uri);
        accessTokenDTO.setState(state);
        String accessToken = gitHubProvider.getAccessToken(accessTokenDTO);
        GitHubUser gitHubUser = gitHubProvider.getUser(accessToken);
        if(gitHubUser!=null){
            User user = new User();
            String token=UUID.randomUUID().toString();
            user.setToken(token);
            user.setName(gitHubUser.getName());
            user.setAccountId(String.valueOf(gitHubUser.getId()));
            user.setAvatarUrl(gitHubUser.getAvatarurl());
            userService.createOrUpdate(user);
            response.addCookie(new Cookie("token",token));
            return "redirect:/";
        }
        else{
            return "redirect:/";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request,
                         HttpServletResponse response){
        request.getSession().removeAttribute("user");
        Cookie cookie = new Cookie("token", null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/";
    }
}
