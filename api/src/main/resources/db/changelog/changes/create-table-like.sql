--liquibase formatted sql
--changeset <litvik>:<create-table-like>
CREATE TABLE IF NOT EXISTS likes (
                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                       user_id BIGINT,
                       post_id BIGINT,
                       FOREIGN KEY (user_id) REFERENCES users(id),
                        FOREIGN KEY (post_id) REFERENCES posts(id)
);
--rollback DROP TABLE likes;