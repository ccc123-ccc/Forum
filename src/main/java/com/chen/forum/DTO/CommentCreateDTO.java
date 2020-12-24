package com.chen.forum.DTO;

import com.chen.forum.model.User;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
public class CommentCreateDTO {
    private Long parentId;
    private Integer type;
    private String content;
}