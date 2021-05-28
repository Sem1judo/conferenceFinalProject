package ua.com.semkov.web.listener;


import org.apache.log4j.Logger;

import javax.servlet.ServletContextAttributeEvent;
import javax.servlet.ServletContextAttributeListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppContextAttributeListener implements ServletContextAttributeListener {
    private static final Logger log = Logger.getLogger(AppContextAttributeListener.class);

    public void attributeAdded(ServletContextAttributeEvent servletContextAttributeEvent) {
        log.debug("ServletContext attribute added::{" + servletContextAttributeEvent.getName() + "," + servletContextAttributeEvent.getValue() + "}");
    }

    public void attributeReplaced(ServletContextAttributeEvent servletContextAttributeEvent) {
        log.debug("ServletContext attribute replaced::{" + servletContextAttributeEvent.getName() + "," + servletContextAttributeEvent.getValue() + "}");
    }

    public void attributeRemoved(ServletContextAttributeEvent servletContextAttributeEvent) {
        log.debug("ServletContext attribute removed::{" + servletContextAttributeEvent.getName() + "," + servletContextAttributeEvent.getValue() + "}");
    }

}

