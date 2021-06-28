CREATE TABLE writer_posts (
                             writer_id BIGINT,
                             post_id BIGINT,
                             FOREIGN KEY (writer_id) REFERENCES writers (id),
                             FOREIGN KEY (post_id) REFERENCES posts (id)
)