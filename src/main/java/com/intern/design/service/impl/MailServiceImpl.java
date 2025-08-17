package com.intern.design.service.impl;

import com.intern.design.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendText(SimpleMailMessage msg) {
        javaMailSender.send(msg);
    }

}
