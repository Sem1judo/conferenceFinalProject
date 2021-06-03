package ua.com.semkov.web.command;

import org.apache.log4j.Logger;
import ua.com.semkov.Path;
import ua.com.semkov.db.entity.User;
import ua.com.semkov.db.dao.impl.UserDaoImpl;
import ua.com.semkov.exceptions.DAOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.core.Config;
import java.io.IOException;

/**
 * Update settings items.
 *
 * @author S.Semkov
 *
 */
public class UpdateSettingsCommand extends Command {

	private static final long serialVersionUID = 7732286214029478505L;

	private static final Logger log = Logger.getLogger(UpdateSettingsCommand.class);

	@Override
	public String execute(HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		log.debug("Command starts");

		User user = (User)request.getSession().getAttribute("user");
		boolean updateUser = false;

		// update first name
		String firstName = request.getParameter("firstName");
		if (firstName != null && !firstName.isEmpty()) {
			user.setFirstName(firstName);
			updateUser = true;
		}

		// update last name
		String lastName = request.getParameter("lastName");
		if (lastName != null && !lastName.isEmpty()) {
			user.setLastName(lastName);
			updateUser = true;
		}

		String localeToSet = request.getParameter("localeToSet");
		if (localeToSet != null && !localeToSet.isEmpty()) {
			HttpSession session = request.getSession();
			Config.set(session, "javax.servlet.jsp.jstl.fmt.locale", localeToSet);
			session.setAttribute("defaultLocale", localeToSet);
			user.setLocaleName(localeToSet);
			updateUser = true;
		}

		if (updateUser) {
			try {
				new UserDaoImpl().updateEntityById(user);
			} catch (DAOException e) {
				e.printStackTrace();
			}
		}


		log.debug("Command finished");
		return Path.PAGE__SETTINGS;
	}

}