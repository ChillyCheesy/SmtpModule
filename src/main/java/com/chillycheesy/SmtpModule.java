package com.chillycheesy;

import com.chillycheesy.exceptions.NoSessionSetException;
import com.chillycheesy.mail.MailBuilder;
import com.chillycheesy.mail.RessourceMailBuilder;
import com.chillycheesy.mail.SimpleMailBuilder;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuleAdapter;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class SmtpModule extends ModuleAdapter {

    protected Session createSession() {
        final String host = defaultConfiguration.getString("host");
        final Properties properties = System.getProperties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", defaultConfiguration.getString("port"));
        properties.put("mail.smtp.ssl.enable", defaultConfiguration.getString("ssl"));
        properties.put("mail.smtp.auth", "true");
        final Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                final String password = defaultConfiguration.getString("password");
                return new PasswordAuthentication(defaultConfiguration.getString("from"), password);
            }
        });
        session.setDebug(defaultConfiguration.getBoolean("debug"));
        return session;
    }

    protected void sendMail(Message message) throws MessagingException {
        Transport.send(message);
    }

    protected void sendMail(MailBuilder mailBuilder) throws Exception {
        Transport.send(mailBuilder.build());
    }
}
