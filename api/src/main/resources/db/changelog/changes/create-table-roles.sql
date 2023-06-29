--liquibase formatted sql
--changeset <litvik>:<create-table-roles>
CREATE TABLE IF NOT EXISTS roles (
id bigint auto_increment,
role_name VARCHAR(50) NOT NULL,
CONSTRAINT roles PRIMARY KEY (id)
);
--rollback DROP TABLE roles;