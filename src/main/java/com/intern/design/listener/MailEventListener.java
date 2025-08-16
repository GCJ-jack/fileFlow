package com.intern.design.listener;

import com.intern.design.config.ThreadPoolConfig;
import com.intern.design.event.BaseEvent;
import com.intern.design.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.message.SimpleMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MailEventListener {

    @Autowired
    MailService mailService;

    @Async(ThreadPoolConfig.EVENT_THREAD_POOL)
    @Retryable(value = Exception.class, maxAttempts = 3, backoff = @Backoff(delay = 1000L))
    @EventListener
    public void listen(BaseEvent<SimpleMailMessage> event){

        SimpleMailMessage simpleMailMessage = event.getData();
        mailService.sendText(simpleMailMessage);
    }

    @Recover
    public void recover(Exception e, BaseEvent<SimpleMailMessage> event) {
        // 记录日志
        log.error("邮件发送失败, 目标邮箱: {}", event.getData().getText(), e);
    }

}
