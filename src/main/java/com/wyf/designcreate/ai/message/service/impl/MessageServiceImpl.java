package com.wyf.designcreate.ai.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.model.entity.Message;
import com.wyf.designcreate.ai.message.service.MessageService;
import com.wyf.designcreate.ai.message.mapper.MessageMapper;
import org.springframework.stereotype.Service;

/**
* @author 15502
* @description 针对表【message(应用会话消息记录表)】的数据库操作Service实现
* @createDate 2026-04-06 14:40:04
*/
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message>
    implements MessageService{

}




