package com.wyf.designcreate.ai.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.model.entity.MessageFeedback;
import com.wyf.designcreate.ai.message.service.MessageFeedbackService;
import com.wyf.designcreate.ai.message.mapper.MessageFeedbackMapper;
import org.springframework.stereotype.Service;

/**
* @author 15502
* @description 针对表【message_feedback(会话消息反馈表)】的数据库操作Service实现
* @createDate 2026-04-06 14:40:04
*/
@Service
public class MessageFeedbackServiceImpl extends ServiceImpl<MessageFeedbackMapper, MessageFeedback>
    implements MessageFeedbackService{

}




