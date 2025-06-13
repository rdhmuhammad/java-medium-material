--liquibase formatted sql

--changeset ridho:create-table-user
create table user
(
    id              BIGSERIAL PRIMARY KEY NOT NULL,
    user_code       VARCHAR(8)            NOT NULL,
    full_name       VARCHAR(100) NULL,
    password        VARCHAR(200)          NOT NULL,
    email           VARCHAR(50)           NOT NULL,
    phone           VARCHAR(16)           NOT NULL,
    address         VARCHAR(200) NULL,
    profile_picture VARCHAR(255)          NOT NULL,
    created_by      VARCHAR(200) NULL,
    created_date    TIMESTAMP NULL,
    modified_by     VARCHAR(200) NULL,
    modified_date   TIMESTAMP NULL

);

--changeset ridho:create-table-config
CREATE TABLE app_configs
(
    id            SMALLINT PRIMARY KEY NOT NULL,
    created_by    VARCHAR(200) NULL,
    created_date  TIMESTAMP            NOT NULL,
    modified_by   VARCHAR(200) NULL,
    modified_date TIMESTAMP NULL,
    is_deleted    BOOLEAN              NOT NULL DEFAULT FALSE,
    key           VARCHAR(100)         NOT NULL,
    value         VARCHAR(100)         NOT NULL
);
