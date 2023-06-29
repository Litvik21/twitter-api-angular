--liquibase formatted sql
--changeset <litvik>:<create-table-users-role>
CREATE TABLE IF NOT EXISTS users_role (
users_id BIGINT NOT NULL,
role_id BIGINT NOT NULL,
PRIMARY KEY (users_id, role_id),
FOREIGN KEY (users_id) REFERENCES users (id),
FOREIGN KEY (role_id) REFERENCES roles (id)
);
--rollback DROP TABLE users_role;