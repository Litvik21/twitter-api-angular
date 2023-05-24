--liquibase formatted sql
--changeset <litvik>:<insert-initial-roles>
INSERT INTO roles (role_name)
VALUES ('ADMIN'), ('USER');