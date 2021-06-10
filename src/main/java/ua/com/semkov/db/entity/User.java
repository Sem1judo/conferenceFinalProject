package ua.com.semkov.db.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * User entity.
 *
 * @author S.Semkov
 */
public class User implements Serializable {

    private static final long serialVersionUID = -6889036256149495388L;

    private Long id;

    private final String login;

    private String password;

    private final String firstName;

    private final String lastName;

    private String localeName;

    private final String phone;

    private final String email;

    private final LocalDateTime registrationDate;

    private final int roleId;

    private final List<Topic> topics;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLocaleName() {
        return localeName;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }


    public static class Builder {


        private final String login;
        private final String password;
        private final String email;
        private final String phone;


        public Builder(String login, String password, String email, String phone) {
            this.login = login;
            this.password = password;
            this.email = email;
            this.phone = phone;
        }

        private Long id;
        private int roleId = 2;
        private String firstName = "Your name";
        private String lastName = "Your last name";
        private LocalDateTime registrationDate = LocalDateTime.now();
        private String localeName = "ru";
        private List<Topic> topics;


        public Builder id(Long idVal) {
            id = idVal;
            return this;
        }

        public Builder roleId(int roleIdVal) {
            roleId = roleIdVal;
            return this;
        }

        public Builder firstName(String firstNameVal) {
            firstName = firstNameVal;
            return this;
        }

        public Builder registrationDate(LocalDateTime registrationDateVal) {
            registrationDate = registrationDateVal;
            return this;
        }

        public Builder lastName(String lastNameVal) {
            lastName = lastNameVal;
            return this;
        }

        public Builder localeName(String localeNameVal) {
            localeName = localeNameVal;
            return this;
        }

        public Builder topics(List<Topic> topicsVal) {
            topics = topicsVal;
            return this;
        }


        public User build() {
            return new User(this);
        }
    }

    private User(Builder builder) {
        id = builder.id;
        login = builder.login;
        password = builder.password;
        email = builder.email;
        firstName = builder.firstName;
        lastName = builder.lastName;
        phone = builder.phone;
        roleId = builder.roleId;
        registrationDate = builder.registrationDate;
        localeName = builder.localeName;
        topics = builder.topics;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", localeName='" + localeName + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", registrationDate=" + registrationDate +
                ", roleId=" + roleId +
                ", topics=" + topics +
                '}';
    }
}
