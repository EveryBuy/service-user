CREATE TABLE IF NOT EXISTS users(
	id BIGSERIAL PRIMARY KEY,
	user_full_name VARCHAR(250),
	creation_date TIMESTAMP WITH TIME ZONE,
	last_activity_date TIMESTAMP WITH TIME ZONE,
	is_block BOOLEAN,
	default_address_id BIGINT,
    delivery_address VARCHAR(100),
	CONSTRAINT fk_user_default_address
	FOREIGN KEY (default_address_id) REFERENCES address(id)
);