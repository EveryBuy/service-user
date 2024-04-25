CREATE TABLE IF NOT EXISTS users(
	id BIGSERIAL PRIMARY KEY,
	user_full_name VARCHAR(250),
    user_photo_url VARCHAR(1000),
	creation_date TIMESTAMP WITH TIME ZONE,
	last_activity_date TIMESTAMP WITH TIME ZONE,
	is_block BOOLEAN,
	default_city_id BIGINT,
	CONSTRAINT fk_user_default_address
	FOREIGN KEY (default_city_id) REFERENCES city(id)
);