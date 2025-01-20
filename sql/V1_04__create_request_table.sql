CREATE TABLE IF NOT EXISTS db_schema.request (
    id_request SERIAL PRIMARY KEY,
    text VARCHAR(150),
    date TIMESTAMP,
    id_audio_mongo VARCHAR,
    id_user INT,
    id_context INT,
    FOREIGN KEY (id_user) REFERENCES db_schema.user (id_user),
    FOREIGN KEY (id_context) REFERENCES db_schema.context (id_context)
);