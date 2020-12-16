package com.example.demo.controller;

import com.example.demo.model.Question;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.GithubUserMapper;
import com.example.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;


@Controller
public class PublishController {
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    private GithubUserMapper githubUserMapper;

    @GetMapping("/publish")
    public String publish () {
        return "publish";
    }

    @PostMapping("/publish")
    public String postPublish (
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            HttpServletRequest request,
            Model model
    ) {
        model.addAttribute ("title", title);
        model.addAttribute ("description", description);
        model.addAttribute ("tag", tag);
        if (title == null || title == "") {
            model.addAttribute ("error", "输入的标题不能为空");
            return "publish";
        }
        if (description == null || description == "") {
            model.addAttribute ("error", "输入的内容不能为空");
            return "publish";
        }
        if (tag == null || tag == "") {
            model.addAttribute ("error", "输入的标签不能为空");
            return "publish";
        }
        String token = null;
        User user = null;
        if (request.getCookies () != null) {
            Cookie[] cookies = request.getCookies ();
            for (Cookie cookie : cookies) {
                if (cookie.getName ().equals ("Token")) {
                    token = cookie.getValue ();
                    user = githubUserMapper.findByToken (token);
                    if (user != null) {
                        request.getSession ().setAttribute ("userPublish", user);

                    }
                    break;
                }
            }
        }

        if (user == null) {
            model.addAttribute ("error", "用户未登陆");
            return "publish";
        }
        Question question = new Question ();
        question.setTitle (title);
        question.setCreator (user.getId ());
        question.setDescription (description);
        question.setTime_create (System.currentTimeMillis ());
        question.setTime_modify (question.getTime_create ());
        question.setTag (tag);
//        question.setId (user.getId ());
        question.setAvatar_url (user.getAvatar_url ());
        questionMapper.insert (question);

        return "redirect:/";
    }
}
