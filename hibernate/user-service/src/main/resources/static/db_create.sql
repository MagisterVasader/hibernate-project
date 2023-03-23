CREATE DATABASE IF NOT EXISTS `user_service`;

USE `user_service`;

CREATE TABLE IF NOT EXISTS `user_service`.`users`
(
    `id`        INT         NOT NULL AUTO_INCREMENT,
    `firstname` VARCHAR(45) NOT NULL,
    `lastname`  VARCHAR(45) NOT NULL,
    PRIMARY KEY (`id`)
);


CREATE TABLE IF NOT EXISTS `user_service`.`roles`
(
    `id`       INT         NOT NULL AUTO_INCREMENT,
    `name`     VARCHAR(45) NOT NULL,
    `bonus`    NUMERIC     NOT NULL,
    `users_id` INT         NOT NULL,
    PRIMARY KEY (`id`),
    FOREIGN KEY (`users_id`)
        REFERENCES `user_service`.`users` (`id`)
        ON DELETE CASCADE
        ON UPDATE CASCADE
)