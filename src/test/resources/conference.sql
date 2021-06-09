

CREATE TABLE IF NOT EXISTS roles
(
    id IDENTITY NOT NULL PRIMARY KEY,
    name  VARCHAR (255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS status
(
    id   IDENTITY NOT NULL PRIMARY KEY,
    name VARCHAR (255) NOT NULL
    );


CREATE TABLE IF NOT EXISTS events
(
    id           IDENTITY NOT NULL PRIMARY KEY,
    title        VARCHAR (255) NOT NULL,
    description  VARCHAR (1024),
    location     VARCHAR (255),
    start_time   timestamp  NOT NULL,
    end_time     timestamp  NOT NULL,
    organizer_id integer,
    status_id    integer,
    CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES status (id) ON UPDATE CASCADE ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS users
(
    id                IDENTITY NOT NULL PRIMARY KEY,
    login             VARCHAR(16) ,
    password          VARCHAR(32) ,
    email             VARCHAR(255) ,
    first_name        VARCHAR(45),
    last_name         VARCHAR(45),
    phone             VARCHAR(16) ,
    role_id           integer DEFAULT 2,
    locale_name       VARCHAR DEFAULT 'ru',
    registration_date timestamp DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE
    );


CREATE TABLE IF NOT EXISTS topics
(
    id         IDENTITY NOT NULL PRIMARY KEY,
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(1024),
    user_id     integer,
    event_id    integer,
    CONSTRAINT fk_user_id_topic FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_event_id_topic FOREIGN KEY (event_id) REFERENCES events (id) ON UPDATE CASCADE ON DELETE CASCADE

    );



CREATE TABLE IF NOT EXISTS users_events
(
    user_id  integer,
    event_id integer,
    CONSTRAINT fk_users_events_event_id FOREIGN KEY (event_id)
    REFERENCES events (id)
    ON UPDATE CASCADE ON DELETE CASCADE,

    CONSTRAINT fk_users_teams_users_id FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON UPDATE CASCADE ON DELETE CASCADE,

    UNIQUE (user_id, event_id)
    );



INSERT INTO roles (id, name)
VALUES (DEFAULT, 'MODERATOR');
INSERT INTO roles (id, name)
VALUES (DEFAULT, 'SPEAKER');
INSERT INTO roles (id, name)
VALUES (DEFAULT, 'CLIENT');

INSERT INTO status (id, name)
VALUES (DEFAULT, 'PLANNED');
INSERT INTO status (id, name)
VALUES (DEFAULT, 'PERFORMING');
INSERT INTO status (id, name)
VALUES (DEFAULT, 'FINISHED');

