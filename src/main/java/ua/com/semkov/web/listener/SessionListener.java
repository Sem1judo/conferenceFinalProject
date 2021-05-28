package ua.com.semkov.web.listener;

import org.apache.log4j.Logger;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

@WebListener
public class SessionListener implements HttpSessionListener {
    private static final Logger log = Logger.getLogger(SessionListener.class);

    public void sessionCreated(HttpSessionEvent sessionEvent) {
        log.debug("Session Created:: ID = "+sessionEvent.getSession().getId());
    }

    public void sessionDestroyed(HttpSessionEvent sessionEvent) {
        log.debug("Session Destroyed:: ID = "+sessionEvent.getSession().getId());
    }

}
