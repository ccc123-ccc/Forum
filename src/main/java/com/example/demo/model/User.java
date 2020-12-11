package com.example.demo.model;

import lombok.Data;

@Data
public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long time_create;
    private Long time_modify;
    private String avatar_url;

}
