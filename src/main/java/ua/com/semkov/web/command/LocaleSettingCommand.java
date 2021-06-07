package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.exceptions.ServiceException;
import ua.com.semkov.service.impl.UserServiceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;


public class LocaleSettingCommand extends Command {
    private static final long serialVersionUID = 7732286214251478505L;

    private static final Logger log = Logger.getLogger(LocaleSettingCommand.class);

    @Override
    public String execute(HttpServletRequest request,
                          HttpServletResponse response) throws IOException {

        log.debug("Command starts");

        User user = (User) request.getSession().getAttribute("user");
        boolean updateUser = false;
        HttpSession session = request.getSession();

        String localeToSet = request.getParameter("localeToSet");
        if (localeToSet != null && !localeToSet.isEmpty()) {
            Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
            session.setAttribute("defaultLocale", localeToSet);
            user.setLocaleName(localeToSet);
            updateUser = true;
        }


        String errorMessage;
        if (updateUser)
            try {
                new UserServiceImpl().updateUser(user);
            } catch (ServiceException e) {
                log.error("Can't update user locale", e);
                errorMessage = "Can't update user locale";
                session.setAttribute("errorMessage", errorMessage);
                return Path.REDIRECT + Path.PAGE__ERROR_PAGE_404;
            }


        log.debug("Command finished");
        return Path.REDIRECT + Path.PAGE__SETTINGS;
    }

}
