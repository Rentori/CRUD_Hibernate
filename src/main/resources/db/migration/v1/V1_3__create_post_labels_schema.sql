CREATE TABLE post_labels (
                             post_id BIGINT,
                             label_id BIGINT,
                             FOREIGN KEY (post_id) REFERENCES posts (id),
                             FOREIGN KEY (label_id) REFERENCES labels (id)
)