package com.example.demo.controller;

import com.example.demo.model.Notification;
import com.example.demo.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class NotificationController {
    @Autowired
    NotificationService notificationService;
    @GetMapping("/notification/{id}")
    public String notification (@PathVariable Integer id){
        Notification notification =notificationService.findAndModifyByNotificationId (id);
        Integer questionId=notification.getOuterId ();
        return "redirect:/question/"+questionId;
    }
}
