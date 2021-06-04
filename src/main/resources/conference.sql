DROP DATABASE IF EXISTS conference;
CREATE DATABASE conference;

DROP TABLE IF EXISTS users_events;
DROP TABLE IF EXISTS events;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS topics;


CREATE TABLE roles
(
    id   SERIAL PRIMARY KEY,
    name character varying(255) NOT NULL
);

CREATE TABLE status
(
    id   SERIAL PRIMARY KEY,
    name character varying(255) NOT NULL
);


CREATE TABLE events
(
    id           SERIAL PRIMARY KEY,
    title        character varying(255) NOT NULL,
    description  character varying(1024),
    location     character varying(255),
    start_time   timestamp without time zone NOT NULL,
    end_time     timestamp without time zone NOT NULL,
    organizer_id integer,
    status_id    integer,
    CONSTRAINT fk_status_id FOREIGN KEY (status_id) REFERENCES status (id) ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE users
(
    id                SERIAL PRIMARY KEY,
    login             character varying(16)  NOT NULL UNIQUE,
    password          character varying(32)  NOT NULL,
    email             character varying(255) NOT NULL UNIQUE,
    first_name        character varying(45),
    last_name         character varying(45),
    phone             character varying(16) UNIQUE,
    role_id           integer                DEFAULT 2,
    event_id          integer,
    locale_name       character varying      DEFAULT 'ru'::character varying,
    registration_date timestamp without time zone DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (id) ON UPDATE CASCADE ON DELETE CASCADE

);


CREATE TABLE topics
(
    id          SERIAL PRIMARY KEY,
    name        character varying(255) NOT NULL,
    description character varying(1024),
    user_id     integer,
    event_id    integer,
    confirm     boolean default false,
    CONSTRAINT fk_user_id FOREIGN KEY (user_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (id) ON UPDATE CASCADE ON DELETE CASCADE

);



CREATE TABLE users_events
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

ALTER TABLE ONLY events
    ADD CONSTRAINT fk_organizer_id FOREIGN KEY (organizer_id) REFERENCES users (id) ON UPDATE CASCADE ON DELETE CASCADE;


INSERT INTO public.roles (id, name)
VALUES (0, 'moderator');
INSERT INTO public.roles (id, name)
VALUES (1, 'speaker');
INSERT INTO public.roles (id, name)
VALUES (2, 'client');

INSERT INTO status (id, name)
VALUES (0, 'planned');
INSERT INTO status (id, name)
VALUES (1, 'performing');
INSERT INTO status (id, name)
VALUES (2, 'finished');




