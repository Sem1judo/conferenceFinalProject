package ua.com.semkov.db;

import ua.com.semkov.db.entity.User;

/**
 * Role entity.
 *
 * @author S.Semkov
 */

public enum Role {
    MODERATOR, SPEAKER, CLIENT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }

}
