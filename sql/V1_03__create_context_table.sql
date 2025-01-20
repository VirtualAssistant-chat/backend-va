CREATE TABLE IF NOT EXISTS db_schema.context (
    id_context SERIAL PRIMARY KEY,
    title VARCHAR(45),
    id_user INT,
    FOREIGN KEY (id_user) REFERENCES db_schema.user (id_user)
);