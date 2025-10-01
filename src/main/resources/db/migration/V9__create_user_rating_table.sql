CREATE TABLE IF NOT EXISTS user_rating(
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
        CONSTRAINT user_id_fk
        FOREIGN KEY (user_id) REFERENCES users(id),
    rater_id BIGINT NOT NULL,
        CONSTRAINT rater_id_fk
        FOREIGN KEY (rater_id) REFERENCES users(id),
    rating SMALLINT NOT NULL
);