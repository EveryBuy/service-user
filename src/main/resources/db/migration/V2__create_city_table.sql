CREATE TABLE IF NOT EXISTS city(
	id SERIAL PRIMARY KEY,
	city_name VARCHAR(100),
    region_id INT,
    CONSTRAINT fk_city_region
               FOREIGN KEY (region_id) REFERENCES region(id)
);