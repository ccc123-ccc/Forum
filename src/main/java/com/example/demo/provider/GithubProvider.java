package com.example.demo.provider;

import ch.qos.logback.core.net.server.Client;
import com.alibaba.fastjson.JSON;
import com.example.demo.DTO.AccessTokenDTO;

import com.example.demo.DTO.GithubUserDTO;
import okhttp3.*;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class GithubProvider {
    public String getAccessToken (AccessTokenDTO accessTokenDTO) {
        MediaType mediaType = MediaType.get ("application/json; charset=utf-8");
        OkHttpClient okHttpClient = new OkHttpClient ();
        RequestBody body = RequestBody.create (mediaType, JSON.toJSONString (accessTokenDTO));
        Request request = new Request.Builder ()
                .url ("https://github.com/login/oauth/access_token")
                .post (body)
                .build ();
        Response response = null;
        try {
            response = okHttpClient.newCall (request).execute ();
        } catch (IOException e) {
            e.printStackTrace ();
        }
        {
            String string = null;
            try {
                string = response.body ().string ();
                String taken= string.split ("&")[0].split ("=")[1 ];
                System.out.println (taken);
                System.out.println (string);
                return taken;

            } catch (IOException e) {
                e.printStackTrace ();
            }


        }
        return null;
    }
    public GithubUserDTO getUser(String AccessToken){
        OkHttpClient okHttpClient = new OkHttpClient ();
        Request request = new Request.Builder ()
                .url("https://api.github.com/user")
                .header("Authorization","token "+AccessToken)
                .build ();

        try {
            Response response = okHttpClient.newCall (request).execute ();
            String string = response.body ().string ();
//            System.out.println (string);
            GithubUserDTO githubUserDTO = JSON.parseObject (string, GithubUserDTO.class);
            return githubUserDTO;
        } catch (IOException e) {
            e.printStackTrace ();
        }


    return  null;}
}
