--liquibase formatted sql
--changeset <litvik>:<create-table-posts>
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_path VARCHAR(255),
    user_id BIGINT,
    date_creating DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );
--rollback DROP TABLE posts;
