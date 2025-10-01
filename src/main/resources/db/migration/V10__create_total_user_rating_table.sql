CREATE TABLE total_user_rating
(
    user_id      BIGINT PRIMARY KEY
        REFERENCES users (id) ON DELETE CASCADE,

    rating_sum   BIGINT NOT NULL DEFAULT 0, -- сумма всех оценок
    rating_count BIGINT NOT NULL DEFAULT 0,
    average_rating NUMERIC(3,2)-- количество оценок

    -- вычисляемое среднее с двумя знаками после запятой
--                                    average_rating NUMERIC(3,2)
--                                        GENERATED ALWAYS AS (
--                                            CASE
--                                                WHEN rating_count = 0 THEN NULL
--                                                ELSE ROUND((rating_sum::numeric / rating_count), 2)
--                                                END
--                                            ) STORED,
--                                    CONSTRAINT chk_rating_nonneg CHECK (rating_sum >= 0 AND rating_count >= 0)
);