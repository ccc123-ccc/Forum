package com.example.demo.DTO;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class QuestionDTO {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long timeCreate;
    private Long timeModify;
    private String creator;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private String avatarUrl;
    private User user;
}


