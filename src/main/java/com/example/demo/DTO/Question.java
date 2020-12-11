package com.example.demo.DTO;

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

    public Integer getId () {
        return id;
    }

    public void setId (Integer id) {
        this.id = id;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getDescription () {
        return description;
    }

    public void setDescription (String description) {
        this.description = description;
    }

    public String getTag () {
        return tag;
    }

    public void setTag (String tag) {
        this.tag = tag;
    }

    public Long getTime_create () {
        return time_create;
    }

    public void setTime_create (Long time_create) {
        this.time_create = time_create;
    }

    public Long getTime_modify () {

        return time_modify;
    }

    public void setTime_modify (Long time_modify) {
        this.time_modify = time_modify;
    }

    public String getCreator () {
        return creator;
    }

    public void setCreator (String creator) {
        this.creator = creator;
    }

    public Integer getComment_count () {
        return comment_count;
    }

    public void setComment_count (Integer comment_count) {
        this.comment_count = comment_count;
    }

    public Integer getView_count () {
        return view_count;
    }

    public void setView_count (Integer view_count) {
        this.view_count = view_count;
    }

    public Integer getLike_count () {
        return like_count;
    }

    public void setLike_count (Integer like_count) {
        this.like_count = like_count;
    }
}
