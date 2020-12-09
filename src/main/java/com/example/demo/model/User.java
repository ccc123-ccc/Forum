package com.example.demo.model;

public class User {
    private Integer id;
    private String name;
    private String accountId;
    private String token;
    private Long time_create;
    private Long time_modify;

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getAccountId () {
        return accountId;
    }

    public void setAccountId (String accountId) {
        this.accountId = accountId;
    }

    public String getToken () {
        return token;
    }

    public Long getTime_create () {
        return time_create;
    }

    public Long getTime_modify () {
        return time_modify;
    }

    public void setToken (String token) {
        this.token = token;
    }


    public void setTime_create (Long time_create) {
        this.time_create = time_create;
    }

    public void setTime_modify (Long time_modify) {
        this.time_modify = time_modify;
    }
}
