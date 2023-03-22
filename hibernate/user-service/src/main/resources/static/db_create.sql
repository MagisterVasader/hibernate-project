CREATE DATABASE IF NOT EXISTS `user_service`;

USE `user_service`;

CREATE TABLE IF NOT EXISTS `user_service`.`users`
(
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `firstname` VARCHAR(45) NOT NULL,
    `lastname`  VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);
