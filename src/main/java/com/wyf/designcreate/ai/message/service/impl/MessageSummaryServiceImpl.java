package com.wyf.designcreate.ai.message.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wyf.designcreate.model.entity.MessageSummary;
import com.wyf.designcreate.ai.message.service.MessageSummaryService;
import com.wyf.designcreate.ai.message.mapper.MessageSummaryMapper;
import org.springframework.stereotype.Service;

/**
* @author 15502
* @description 针对表【message_summary(会话摘要表（与消息表分离存储）)】的数据库操作Service实现
* @createDate 2026-04-06 14:40:04
*/
@Service
public class MessageSummaryServiceImpl extends ServiceImpl<MessageSummaryMapper, MessageSummary>
    implements MessageSummaryService{

}




