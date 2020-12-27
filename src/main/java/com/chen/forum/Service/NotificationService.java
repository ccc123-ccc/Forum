package com.chen.forum.Service;

import com.chen.forum.DTO.NotificationDTO;
import com.chen.forum.DTO.PageDTO;
import com.chen.forum.DTO.QuestionDTO;
import com.chen.forum.enums.NotificationStatusEnum;
import com.chen.forum.enums.NotificationTypeEnum;
import com.chen.forum.exception.CustomizeErrorCode;
import com.chen.forum.exception.CustomizeException;
import com.chen.forum.mapper.NotificationMapper;
import com.chen.forum.mapper.UserMapper;
import com.chen.forum.model.*;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class NotificationService {
    @Autowired
    private NotificationMapper notificationMapper;
    @Autowired
    private UserMapper userMapper;
    public PageDTO list(Long userId, Integer page, Integer size) {
        Integer offset=size*(page-1);
        NotificationExample example1 = new NotificationExample();
        example1.createCriteria().andReceiverEqualTo(userId);
        example1.setOrderByClause("gmt_create desc");
        List<Notification> list = notificationMapper.selectByExampleWithRowbounds(example1, new RowBounds(offset, size));
        ArrayList<NotificationDTO> notificationDTOS = new ArrayList<>();
        PageDTO pageDTO = new PageDTO();
        for(Notification notification:list){
            NotificationDTO notificationDTO = new NotificationDTO();
            BeanUtils.copyProperties(notification,notificationDTO);
            notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
            notificationDTOS.add(notificationDTO);
        }
        pageDTO.setData(notificationDTOS);
        NotificationExample example = new NotificationExample();
        example.createCriteria().andReceiverEqualTo(userId);
        Integer totalcount = (int)notificationMapper.countByExample(example);
        pageDTO.setPageDTO(totalcount,page,size);
        return pageDTO;
    }

    public Long unreadCount(Long userId) {
        NotificationExample notificationExample = new NotificationExample();
        notificationExample.createCriteria()
                .andReceiverEqualTo(userId)
                .andStatusEqualTo(NotificationStatusEnum.UNREAD.getStatus());
        return notificationMapper.countByExample(notificationExample);
    }

    public NotificationDTO read(Long id, User user) {
        Notification notification = notificationMapper.selectByPrimaryKey(id);
        if (notification == null) {
            throw new CustomizeException(CustomizeErrorCode.NOTIFICATION_NOT_FOUND);
        }
        if (!Objects.equals(notification.getReceiver(), user.getId())) {
            throw new CustomizeException(CustomizeErrorCode.READ_NOTIFICATION_FAIL);
        }

        notification.setStatus(NotificationStatusEnum.READ.getStatus());
        notificationMapper.updateByPrimaryKey(notification);

        NotificationDTO notificationDTO = new NotificationDTO();
        BeanUtils.copyProperties(notification, notificationDTO);
        notificationDTO.setTypeName(NotificationTypeEnum.nameOfType(notification.getType()));
        return notificationDTO;
    }
}
