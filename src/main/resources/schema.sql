DROP TABLE IF EXISTS error_item;
CREATE TABLE error_item (
    id        BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    level     VARCHAR(100),
    position  VARCHAR(100),
    error     VARCHAR(100));



DROP TABLE IF EXISTS landing_grade;
CREATE TABLE landing_grade (
    id              BIGINT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    timestamp       TIMESTAMP,
    raw_grade       VARCHAR(100),
    carrier_name    VARCHAR(100),
    aircraft_type   VARCHAR(100),
    grade           VARCHAR(100),
    wire            VARCHAR(100));



DROP TABLE IF EXISTS landing_grade_error_item;
CREATE TABLE landing_grade_error_item (
    landing_grade_id     BIGINT NOT NULL,
    error_item_id        BIGINT NOT NULL);

