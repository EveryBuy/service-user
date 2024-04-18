CREATE TABLE IF NOT EXISTS users_role(
	user_id BIGINT REFERENCES users(id),
	role_id BIGINT REFERENCES role(id),
	PRIMARY KEY (user_id, role_id)
);