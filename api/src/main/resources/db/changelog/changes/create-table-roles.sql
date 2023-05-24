--liquibase formatted sql
--changeset <litvik>:<create-table-roles>
CREATE TABLE IF NOT EXISTS roles
(
    id bigint auto_increment,
    role_name varchar(255) not null,
    CONSTRAINT roles_pk PRIMARY KEY (id)
    );
--rollback DROP TABLE roles;