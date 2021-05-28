package ua.com.semkov.web.command;

import java.util.Map;
import java.util.TreeMap;

import org.apache.log4j.Logger;
import ua.com.semkov.web.command.clientCommand.JoinUserEventsCommand;
import ua.com.semkov.web.command.eventCommand.CreateEventCommand;
import ua.com.semkov.web.command.eventCommand.DeleteEventCommand;
import ua.com.semkov.web.command.eventCommand.ListEventsCommand;
import ua.com.semkov.web.command.eventCommand.UpdateEventCommand;
import ua.com.semkov.web.command.profile.UserListEventsCommand;
import ua.com.semkov.web.command.speaker.ProposeSpeakerTopicCommand;
import ua.com.semkov.web.command.topicCommand.CreateTopicCommand;
import ua.com.semkov.web.command.topicCommand.DeleteTopicCommand;
import ua.com.semkov.web.command.topicCommand.ListTopicsCommand;
import ua.com.semkov.web.command.topicCommand.UpdateTopicCommand;

/**
 * Holder for all commands.<br/>
 *
 * @author S.Semkov
 */
public class CommandContainer {

    private static final Logger log = Logger.getLogger(CommandContainer.class);

    private static Map<String, Command> commands = new TreeMap<String, Command>();

    static {
        // common commands
        commands.put("registration", new RegistrationCommand());
        commands.put("login", new LoginCommand());
        commands.put("logout", new LogoutCommand());
        commands.put("noCommand", new NoCommand());
        commands.put("viewSettings", new ViewSettingsCommand());
        commands.put("updateSettings", new UpdateSettingsCommand());
        commands.put("profileListEvents", new UserListEventsCommand());
        commands.put("joinEvent", new JoinUserEventsCommand());
        commands.put("proposeTopicSpeaker", new ProposeSpeakerTopicCommand());




        // admin commands
        commands.put("listEvents", new ListEventsCommand());
        commands.put("createEvent", new CreateEventCommand());
        commands.put("deleteEvent", new DeleteEventCommand());
        commands.put("updateEvent", new UpdateEventCommand());

        commands.put("listTopics", new ListTopicsCommand());
        commands.put("deleteTopic", new DeleteTopicCommand());
        commands.put("updateTopic", new UpdateTopicCommand());
        commands.put("createTopic", new CreateTopicCommand());

        log.debug("Command container was successfully initialized");
        log.trace("Number of commands --> " + commands.size());
    }

    /**
     * Returns command object with the given name.
     *
     * @param commandName Name of the command.
     * @return Command object.
     */
    public static Command get(String commandName) {
        if (commandName == null || !commands.containsKey(commandName)) {
            log.trace("Command not found, name --> " + commandName);
            return commands.get("noCommand");
        }

        return commands.get(commandName);
    }

}