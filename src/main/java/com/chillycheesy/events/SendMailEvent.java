package com.chillycheesy.events;

import com.chillycheesy.modulo.events.Event;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;

public class SendMailEvent extends Event {
    protected String from = "";
    protected String[] to = new String[0];
    protected String subject = "";
    protected String content = "";
    protected String[] cc = new String[0];
    protected String[] bcc = new String[0];

    public SendMailEvent(String from, String[] to, String subject, String content, String[] cc, String[] bcc) {
        this.from = from;
        this.to = to;
        this.subject = subject;
        this.content = content;
        this.cc = cc;
        this.bcc = bcc;
    }

    public SendMailEvent(Message message) throws MessagingException, IOException {
        this(
                message.getFrom().toString(),
                Arrays.stream(message.getRecipients(Message.RecipientType.TO)).map(Address::toString).toArray(String[]::new),
                message.getSubject(),
                message.getContent().toString(),
                Arrays.stream(message.getRecipients(Message.RecipientType.CC)).map(Address::toString).toArray(String[]::new),
                Arrays.stream(message.getRecipients(Message.RecipientType.BCC)).map(Address::toString).toArray(String[]::new)
        );
    }

    @Override
    public String toString() {
        return "SendMailEvent{" +
                "from='" + from + '\'' +
                ", to=" + Arrays.toString(to) +
                ", subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", cc=" + Arrays.toString(cc) +
                ", bcc=" + Arrays.toString(bcc) +
                '}';
    }
}
