CREATE TABLE IF NOT EXISTS address(
	id BIGSERIAL PRIMARY KEY,
	street_name VARCHAR(200),
	house_number INT,
	city_id INT,
	CONSTRAINT fk_city_address
	FOREIGN KEY (city_id) REFERENCES city(id)
);