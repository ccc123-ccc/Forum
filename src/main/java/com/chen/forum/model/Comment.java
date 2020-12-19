package com.chen.forum.model;

import lombok.Data;

@Data
public class Comment {
    private Integer id;
    private Integer parent_id;
    private String content;
    private Integer type;
    private Integer commentator;
    private long gmt_modified;
    private long gmt_create;
    private Integer like_count;
}
