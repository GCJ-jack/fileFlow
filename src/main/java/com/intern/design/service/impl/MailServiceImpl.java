package com.intern.design.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl {

    @Autowired
    JavaMailSender javaMailSender;

    public void sendText(SimpleMailMessage msg) {
        javaMailSender.send(msg);
    }

}
