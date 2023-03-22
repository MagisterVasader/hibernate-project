CREATE DATABASE IF NOT EXISTS `employee_service`;

USE `employee_service`;

CREATE TABLE IF NOT EXISTS `employee_service`.`employees`
(
    `id`      LONG        NOT NULL AUTO_INCREMENT,
    `name`    VARCHAR(45) NOT NULL,
    `salary`  NUMERIC     NOT NULL,
    `version` INT         NOT NULL DEFAULT 0,
    PRIMARY KEY (`id`)
);
