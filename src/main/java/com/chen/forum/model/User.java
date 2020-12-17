package com.chen.forum.model;

import lombok.Data;

import java.util.concurrent.PriorityBlockingQueue;
@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private long gmtCreate;
    private long gmtModified;
    private String avatarurl;
}
