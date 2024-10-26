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
