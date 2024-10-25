--liquibase formatted sql

------------------------------------------------------------------------

--changeset Dimitris.Bakalis:1.0.0-1

------------------------------------------------------------------------

CREATE TABLE sep_user
(
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    username VARCHAR(64) NOT NULL,
    hash VARCHAR (256) NOT NULL,
    role VARCHAR(64) NOT NULL
);


INSERT INTO sep_user (username, hash, role) VALUES ('bakalis', '$2y$10$43jfmaTXya8yJxXWvHkyV.8t6yqaIaGAEJe.TDUKCY7psO8nvVCzu', 'SENIOR_CUSTOMER_SUPPORT_OFFICER');

------------------------------------------------------------------------
