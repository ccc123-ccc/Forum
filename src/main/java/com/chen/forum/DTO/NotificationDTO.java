package com.chen.forum.DTO;

import com.chen.forum.model.User;
import lombok.Data;

@Data
public class NotificationDTO {
    private Long id;
    private Long notifier;
    private Long outerid;
    private String typeName;
    private Long gmtCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private Integer type;
}