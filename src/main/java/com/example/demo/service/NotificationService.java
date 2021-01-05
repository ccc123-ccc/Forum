package com.example.demo.service;

import com.example.demo.DTO.NotificationDTO;
import com.example.demo.DTO.PagesDTO;
import com.example.demo.DTO.QuestionDTO;
import com.example.demo.enums.NotificationStatusEnums;
import com.example.demo.enums.NotificationTypeEnums;
import com.example.demo.mapper.NotificationMapper;
import com.example.demo.mapper.UserMapper;
import com.example.demo.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
//现在需要评论的人（NotifierName） 接受通知的人（通过user得到userId） type的message 就够了
//需要找到接受通知的人与通知的receiver一样的列表
@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    UserMapper userMapper;

    public  Integer getUnreadCount (User user) {
        Integer userId=user.getId ();
        NotificationExample example = new NotificationExample ();
        example.createCriteria ()
                .andReceiverEqualTo (userId);
        List<Notification> notifications = notificationMapper.selectByExample (example);
        int count=0;
        for (Notification notification : notifications) {
            if(notification.getStatus ()==0){
                count++;
            }
        }
        return count;

    }

    public PagesDTO list (Integer id, Integer page, Integer size) {
        Integer offset = (page - 1) * size;
        NotificationExample example1 = new NotificationExample ();
        example1.createCriteria ()
                .andReceiverEqualTo (id);
        example1.setOrderByClause ("time_create desc");
        List<Notification> notifications = notificationMapper.selectByExampleWithRowbounds (
                example1, new RowBounds (offset, size));
        PagesDTO pagesDTO = new PagesDTO ();
        if(notifications.size ()==0){
            return pagesDTO;
        }
        List<NotificationDTO> notificationDTOS = new ArrayList<> ();
        for (Notification notification : notifications) {
            NotificationDTO notificationDTO = new NotificationDTO ();
            BeanUtils.copyProperties (notification, notificationDTO);
            notificationDTO.setMessage (NotificationTypeEnums.nameOfType (notification.getType ()));
            notificationDTOS.add(notificationDTO);
        }
        pagesDTO.setData (notificationDTOS);
        NotificationExample example = new NotificationExample ();
        example.createCriteria ()
                .andReceiverEqualTo (id);
        Integer count = (int) notificationMapper.countByExample (example);
        pagesDTO.setPages (page, size, count);
        return pagesDTO;
    }

    public Notification findAndModifyByNotificationId (Integer id) {
        Notification notification = notificationMapper.selectByPrimaryKey (id);
        notification.setStatus (NotificationStatusEnums.READ.getStatus ());
        notificationMapper.updateByPrimaryKey (notification);
        return notification;
    }

    public Integer unReadCount () {
        NotificationExample example = new NotificationExample ();
        example.createCriteria ()
                .andStatusEqualTo (0);
        Integer count = Integer.valueOf ((int) notificationMapper.countByExample (example));
        return count;
    }
}
