package com.chillycheesy.mail;

import javax.mail.Message;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RessourceMailBuilder extends SimpleMailBuilder {

    @Override
    public SimpleMailBuilder content(String content) throws Exception {
        final InputStream is = getClass().getClassLoader().getResourceAsStream(content);
        final InputStreamReader isr = new InputStreamReader(is);
        final BufferedReader reader = new BufferedReader(isr);
        final StringBuilder sb = new StringBuilder();
        for (String line; (line = reader.readLine()) != null; ) {
            sb.append(line);
        }
        return super.content(sb.toString());
    }

    @Override
    public Message build() throws Exception {
        final Message message = super.build();
        message.setContent(this.content, "text/html");
        return message;
    }
}
