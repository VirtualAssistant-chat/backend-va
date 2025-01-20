CREATE TABLE IF NOT EXISTS db_schema.response (
    id_response SERIAL PRIMARY KEY,
    id_request INT,
    text VARCHAR(150),
    date TIMESTAMP,
    FOREIGN KEY (id_request) REFERENCES db_schema.request (id_request)
);