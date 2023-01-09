package com.chillycheesy.mail;

import com.chillycheesy.exceptions.NoSessionSetException;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;

public interface MailBuilder {
    Message build() throws Exception;
    SimpleMailBuilder session(Session session) throws Exception;
    SimpleMailBuilder from(String from) throws Exception;
    SimpleMailBuilder to(String... to) throws Exception;
    SimpleMailBuilder subject(String subject) throws Exception;
    SimpleMailBuilder content(String content) throws Exception;
    SimpleMailBuilder cc(String... cc) throws Exception;
    SimpleMailBuilder bcc(String... bcc) throws Exception;
}
