package com.chillycheesy.mail;

import com.chillycheesy.exceptions.NoSessionSetException;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleMailBuilder implements MailBuilder {
    protected Session session;
    protected String from = "";
    protected Address[] to = new Address[0];
    protected String subject = "";
    protected String content = "";
    protected Address[] cc = new Address[0];
    protected Address[] bcc = new Address[0];

    public Message build() throws Exception {
        if (this.session == null) throw new NoSessionSetException();
        final Message message = new MimeMessage(this.session);
        message.setFrom(new InternetAddress(this.from));
        message.setRecipients(Message.RecipientType.TO, this.to);
        message.setSubject(this.subject);
        message.setContent(this.content, "text/plain");
        message.setRecipients(Message.RecipientType.CC, this.cc);
        message.setRecipients(Message.RecipientType.BCC, this.bcc);
        return message;
    }

    public SimpleMailBuilder session(Session session) {
        this.session = session;
        return this;
    }

    public SimpleMailBuilder from(String from) {
        this.from = from;
        return this;
    }

    public SimpleMailBuilder to(String... to) throws Exception {
        final List<Address> addresses = this.stringsToAddresses(to);
        this.to = new Address[addresses.size()];
        addresses.toArray(this.to);
        return this;
    }

    public SimpleMailBuilder subject(String subject) {
        this.subject = subject;
        return this;
    }

    public SimpleMailBuilder content(String content) throws Exception {
        this.content = content;
        return this;
    }

    public SimpleMailBuilder cc(String... cc) throws Exception {
        final List<Address> addresses = this.stringsToAddresses(cc);
        this.cc = new Address[addresses.size()];
        addresses.toArray(this.cc);
        return this;
    }

    public SimpleMailBuilder bcc(String... bcc) throws Exception {
        final List<Address> addresses = this.stringsToAddresses(bcc);
        this.bcc = new Address[addresses.size()];
        addresses.toArray(this.bcc);
        return this;
    }

    private List<Address> stringsToAddresses(String[] strings) throws Exception {
        List<Address> addresses = new ArrayList<Address>();
        for (String address : strings) {
            addresses.add(new InternetAddress(address));
        }
        return addresses;
    }
}
