UPDATE users
SET last_activity_date = creation_date
WHERE last_activity_date IS NULL;