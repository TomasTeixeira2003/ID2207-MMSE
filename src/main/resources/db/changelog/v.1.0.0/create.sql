--liquibase formatted sql

------------------------------------------------------------------------

--changeset Dimitris.Bakalis:1.0.0-1

------------------------------------------------------------------------

CREATE TABLE sep_user
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(64) NOT NULL,
    hash VARCHAR (256) NOT NULL,
    role VARCHAR(64) NOT NULL,
    active BOOL NOT NULL DEFAULT true
);


INSERT INTO sep_user (username, hash, role) VALUES ('bakalis', '$2y$10$43jfmaTXya8yJxXWvHkyV.8t6yqaIaGAEJe.TDUKCY7psO8nvVCzu', 'PRODUCTION_MANAGER');
INSERT INTO sep_user (username, hash, role) VALUES ('admin', '$2y$10$43jfmaTXya8yJxXWvHkyV.8t6yqaIaGAEJe.TDUKCY7psO8nvVCzu', 'ADMINISTRATION_MANAGER');

------------------------------------------------------------------------


--changeset Dimitris.Bakalis:1.0.0-2

------------------------------------------------------------------------

CREATE TABLE task
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    project VARCHAR(1024) NOT NULL,
    description TEXT NOT NULL,
    status VARCHAR(64) NOT NULL,
    assignee_id BIGINT NOT NULL,
    priority VARCHAR(64) NOT NULL
);

------------------------------------------------------------------------


--changeset Dimitris.Bakalis:1.0.0-3

------------------------------------------------------------------------

CREATE TABLE event_planning_request
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    client_record_number VARCHAR(512),
    client_name VARCHAR(512),
    event_type VARCHAR(512),
    description TEXT,
    event_start DATE,
    event_end DATE,
    number_of_attendants INTEGER,
    planned_budget NUMERIC,
    decorations TEXT,
    food_and_drinks TEXT,
    music TEXT,
    photos TEXT,
    posters TEXT,
    computer_related TEXT,
    other_needs TEXT,
    status VARCHAR (64),
    requested_by VARCHAR (512),
    priority VARCHAR (256),
    assigned_to_role VARCHAR (256)
);

------------------------------------------------------------------------


--changeset Dimitris.Bakalis:1.0.0-4

------------------------------------------------------------------------

CREATE TABLE recruitment_request
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    contract_type VARCHAR (64),
    requesting_department VARCHAR (64),
    experience VARCHAR (512),
    job_title VARCHAR (512),
    job_description VARCHAR (1024),
    created_by VARCHAR (512),
    status VARCHAR (64),
    priority VARCHAR (64),
    assigned_to_role VARCHAR (64)
);

------------------------------------------------------------------------
