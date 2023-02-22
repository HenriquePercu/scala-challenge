CREATE DATABASE myimdb;
\c myimdb;

CREATE TABLE directors
(
    id              serial            NOT NULL,
    PRIMARY KEY (id),
    email           character varying NOT NULL,
    formatted_email character varying NOT NULL
);