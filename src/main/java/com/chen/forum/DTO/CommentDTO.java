package com.chen.forum.DTO;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;

@Data
public class CommentDTO {
    private long parent_id;
    private String content;
    private Integer type;
}
