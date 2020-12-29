package com.example.demo.DTO;

import com.example.demo.model.User;
import lombok.Data;

@Data
public class CommentDTO {
    private Integer id;
    private Integer parentId;
    private Integer commentator;
    private Long timeCreate;
    private Long timeModify;
    private Long likeCount;
    private String content;
    private Integer type;
    private Integer commentCount;
    private User user;
}
