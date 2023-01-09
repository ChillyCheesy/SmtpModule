package com.chillycheesy;

import com.chillycheesy.events.SendMailEvent;
import com.chillycheesy.exceptions.NoSessionSetException;
import com.chillycheesy.mail.MailBuilder;
import com.chillycheesy.mail.RessourceMailBuilder;
import com.chillycheesy.mail.SimpleMailBuilder;
import com.chillycheesy.modulo.ModuloAPI;
import com.chillycheesy.modulo.events.EventContainer;
import com.chillycheesy.modulo.events.EventManager;
import com.chillycheesy.modulo.modules.Module;
import com.chillycheesy.modulo.modules.ModuleAdapter;

import javax.mail.*;
import java.io.IOException;
import java.util.Properties;

public class SmtpModule extends ModuleAdapter {
    private EventManager eventManager;

    @Override
    protected <E extends Throwable> void onStart() throws E {
        this.eventManager = ModuloAPI.getEvent().getEventManager();
    }

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

    protected void sendMail(Message message) throws MessagingException, IOException {
        Transport.send(message);
        this.eventManager.emitEvent(this, new SendMailEvent(message));
    }

    protected void sendMail(MailBuilder mailBuilder) throws Exception {
        this.sendMail(mailBuilder.build());
    }
}
