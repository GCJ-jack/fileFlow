package com.intern.design.service;

import org.springframework.mail.SimpleMailMessage;

public interface MailService {

    void sendText(SimpleMailMessage simpleMailMessage);
}
