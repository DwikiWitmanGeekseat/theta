-- Change Set 1673930216-1
-- Author: thetaDev
CREATE TABLE person (
    id BIGINT AUTO_INCREMENT NOT NULL,
    created DATETIME(6),
    creator VARCHAR(255),
    creator_id VARCHAR(50),
    edited DATETIME(6),
    editor VARCHAR(255),
    editor_id VARCHAR(50),
    storage_map TEXT,
    active SMALLINT,
    birth DATE,
    email VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Change Set 1673930216-2
-- Author: thetaDev
CREATE TABLE role (
    id BIGINT AUTO_INCREMENT NOT NULL,
    created DATETIME(6),
    creator VARCHAR(255),
    creator_id VARCHAR(50),
    edited DATETIME(6),
    editor VARCHAR(255),
    editor_id VARCHAR(50),
    storage_map TEXT,
    active SMALLINT,
    birth DATE,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Change Set 1673930216-3
-- Author: thetaDev
CREATE TABLE person_roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    roles_id BIGINT,
    person_id BIGINT
);

-- Change Set 1673930216-4
-- Author: thetaDev
CREATE TABLE product (
    id BIGINT AUTO_INCREMENT NOT NULL,
    created DATETIME(6),
    creator VARCHAR(255),
    creator_id VARCHAR(50),
    edited DATETIME(6),
    editor VARCHAR(255),
    editor_id VARCHAR(50),
    storage_map TEXT,
    active SMALLINT,
    birth DATE,
    name VARCHAR(255) NOT NULL,
    quantity INT NOT NULL,
    slug VARCHAR(255) NOT NULL,
    shop_id BIGINT NOT NULL,
    PRIMARY KEY (id)
);

-- Change Set 1673930216-5
-- Author: thetaDev
CREATE TABLE shop (
    id BIGINT AUTO_INCREMENT NOT NULL,
    created DATETIME(6),
    creator VARCHAR(255),
    creator_id VARCHAR(50),
    edited DATETIME(6),
    editor VARCHAR(255),
    editor_id VARCHAR(50),
    storage_map TEXT,
    active SMALLINT,
    birth DATE,
    name VARCHAR(255) NOT NULL,
    slug VARCHAR(255) NOT NULL,
    PRIMARY KEY (id)
);

-- Change Set 1673930216-6
-- Author: thetaDev
ALTER TABLE person_roles
    ADD CONSTRAINT fk_rolesid_role
    FOREIGN KEY (roles_id)
    REFERENCES role (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

-- Change Set 1673930216-7
-- Author: thetaDev
ALTER TABLE product
    ADD CONSTRAINT fk_shopid_shop
    FOREIGN KEY (shop_id)
    REFERENCES shop (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

-- Change Set 1673930216-8
-- Author: thetaDev
ALTER TABLE person_roles
    ADD CONSTRAINT fk_personid_person
    FOREIGN KEY (person_id)
    REFERENCES person (id)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

-- Change Set 1673930216-9
-- Author: thetaDev
-- This change set references an SQL file: sql/v1-initialdata.sql
-- The content of the SQL file should be included here.
-- Please make sure the SQL file contains valid SQL statements.
