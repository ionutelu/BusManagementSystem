-- V2__add_app_users.sql
-- Application users table for JWT authentication.

CREATE TABLE app_users (
    id       BIGINT       NOT NULL AUTO_INCREMENT,
    username VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    role     VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT uq_app_users_username UNIQUE (username)
) ENGINE = InnoDB;

