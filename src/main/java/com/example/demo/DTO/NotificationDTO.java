package com.example.demo.DTO;

import lombok.Data;

@Data
public class NotificationDTO {
    private Integer id;
    private Integer notifier;
    private Integer receiver;
    private Integer outerId;
    private Integer type;
    private Long timeCreate;
    private Integer status;
    private String notifierName;
    private String outerTitle;
    private String message;
}
