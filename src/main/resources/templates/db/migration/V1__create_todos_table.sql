CREATE TABLE todos (
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL DEFAULT 'NEW',
    created_at  TIMESTAMP,
    updated_at  TIMESTAMP
);