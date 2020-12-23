package com.example.demo.controller;

import com.example.demo.DTO.QuestionDTO;
import com.example.demo.model.Question;
import com.example.demo.mapper.QuestionMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.model.UserExample;
import com.example.demo.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.yaml.snakeyaml.events.Event;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class PublishController {
    @Autowired
    private QuestionService questionService;
    @Autowired
    QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name="id")Integer id,
                       Model model){
        QuestionDTO question = questionService.getById (id);
        model.addAttribute ("title", question.getTitle ());
        model.addAttribute ("description", question.getDescription ());
        model.addAttribute ("tag", question.getTag ());
        model.addAttribute ("id",question.getId ());
        return "publish";
    }

    @GetMapping("/publish")
    public String publish () {
        return "publish";
    }

    @PostMapping("/publish")
    public String postPublish (
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("tag") String tag,
            @RequestParam(value = "id",required = false) Integer id,
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
                    UserExample userExample = new UserExample ();
                    userExample.createCriteria ()
                            .andTokenEqualTo (token);
                    List<User> users = userMapper.selectByExample (userExample);
                    if (users.size ()!=0) {
                        user=users.get(0);
                        request.getSession ().setAttribute ("user", users.get (0));
                    }
                    break;
                }
            }
        }

        if (user==null) {
            model.addAttribute ("error", "用户未登陆");
            return "publish";
        }
        Question question = new Question ();
        question.setTitle (title);
        question.setCreator  (String .valueOf (user.getId ()));
        question.setDescription (description);
        question.setTag (tag);
        question.setAvatarUrl (user.getAvatarUrl ());
        question.setId (id);
        question.setTimeCreate (System.currentTimeMillis ());
        questionService.createOrUpdate(question);
        return "redirect:/";
    }
}
