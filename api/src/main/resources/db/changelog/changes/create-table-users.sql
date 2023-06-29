--liquibase formatted sql
--changeset <litvik>:<create-table-users>
CREATE TABLE IF NOT EXISTS users
(
    id bigint auto_increment,
    username varchar(600) not null,
    password varchar(255) not null,
    role_id bigint,
    FOREIGN KEY (role_id) REFERENCES roles(id),
    CONSTRAINT users PRIMARY KEY (id)
    );
--rollback DROP TABLE users;