--liquibase formatted sql
--changeset <litvik>:<create-table-posts>
CREATE TABLE IF NOT EXISTS posts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    image_path VARCHAR(255),
    user_id BIGINT,
    date_creating DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
    );

CREATE TABLE IF NOT EXISTS posts_likes (
    posts_id BIGINT,
    likes_id BIGINT,
    FOREIGN KEY (posts_id) REFERENCES posts(id),
    FOREIGN KEY (likes_id) REFERENCES likes(id),
    CONSTRAINT FK_posts_likes_posts FOREIGN KEY (posts_id) REFERENCES posts(id)
    );
--rollback DROP TABLE posts;
