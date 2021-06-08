BEGIN;


CREATE TABLE public.events
(
    id integer NOT NULL,
    title character varying(255) NOT NULL,
    description character varying(1024),
    location character varying(255),
    organizer_id integer,
    status_id integer,
    start_time timestamp without time zone,
    end_time timestamp without time zone,
    PRIMARY KEY (id)
);

CREATE TABLE public.roles
(
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.status
(
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE public.topics
(
    id integer NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(1024),
    user_id integer,
    event_id integer,
    confirm boolean,
    PRIMARY KEY (id)
);

CREATE TABLE public.users
(
    id integer NOT NULL,
    login character varying(16) NOT NULL,
    password character varying(32) NOT NULL,
    email character varying(255) NOT NULL,
    first_name character varying(45),
    last_name character varying(45),
    phone character varying(16),
    role_id integer,
    locale_name character varying,
    registration_date time without time zone,
    PRIMARY KEY (id)
);

CREATE TABLE public.users_events
(
    user_id integer,
    event_id integer
);

ALTER TABLE public.events
    ADD FOREIGN KEY (status_id)
        REFERENCES public.status (id)
    NOT VALID;


ALTER TABLE public.events
    ADD FOREIGN KEY (organizer_id)
        REFERENCES public.users (id)
    NOT VALID;


ALTER TABLE public.events
    ADD FOREIGN KEY (status_id)
        REFERENCES public.status (id)
    NOT VALID;


ALTER TABLE public.topics
    ADD FOREIGN KEY (event_id)
        REFERENCES public.events (id)
    NOT VALID;


ALTER TABLE public.topics
    ADD FOREIGN KEY (user_id)
        REFERENCES public.users (id)
    NOT VALID;


ALTER TABLE public.users
    ADD FOREIGN KEY (role_id)
        REFERENCES public.roles (id)
    NOT VALID;


ALTER TABLE public.users_events
    ADD FOREIGN KEY (event_id)
        REFERENCES public.events (id)
    NOT VALID;


ALTER TABLE public.users_events
    ADD FOREIGN KEY (user_id)
        REFERENCES public.users (id)
    NOT VALID;

END;