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

    private String login;

    private String password;

    private String firstName;

    private String lastName;

    private String localeName;

    private String phone;

    private String email;

    private LocalDateTime registrationDate;

    private int roleId;

    private List<Topic> topics;

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocaleName() {
        return localeName;
    }

    public void setLocaleName(String localeName) {
        this.localeName = localeName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDateTime registrationDate) {
        this.registrationDate = registrationDate;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public User() {
    }

    public static class Builder {


        private final String username;
        private final String password;
        private final String email;
        private final String phone;


        public Builder(String username, String password, String email, String phone) {
            this.username = username;
            this.password = password;
            this.email = email;
            this.phone = phone;
        }

        private Long id;
        private int roleId = 2;
        private String firstName = "First name";
        private String lastName = "Last Name";
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
        login = builder.username;
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
