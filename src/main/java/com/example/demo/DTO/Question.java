package com.example.demo.DTO;

import lombok.Data;

@Data
public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Long time_create;
    private Long time_modify;
    private String creator;
    private Integer comment_count;
    private Integer view_count;
    private Integer like_count;
    private String avatar_url;
}
