package ua.com.semkov.db.entity;


import java.io.Serializable;

/**
 * Role entity.
 *
 * @author S.Semkov
 */
public enum Role implements Serializable {
    ADMIN, MODERATOR, CLIENT;

    public static Role getRole(User user) {
        int roleId = user.getRoleId();
        return Role.values()[roleId];
    }

    public String getName() {
        return name().toLowerCase();
    }
}