--liquibase formatted sql
--changeset <litvik>:<insert-main-roles>
INSERT INTO roles (role_name)
VALUES
('ADMIN'),
('USER');