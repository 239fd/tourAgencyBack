CREATE SCHEMA IF NOT EXISTS `touragency` DEFAULT CHARACTER SET utf8mb4;
USE `touragency`;

CREATE TABLE IF NOT EXISTS `touragency`.`languages`
(
    `id`       INT          NOT NULL AUTO_INCREMENT,
    `language` VARCHAR(255) NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB;

CREATE TABLE IF NOT EXISTS `touragency`.`users`
(
    `id`              BIGINT                 NOT NULL AUTO_INCREMENT,
    `active`          BIT(1)                 NULL DEFAULT NULL,
    `age`             INT                    NULL DEFAULT NULL,
    `email`           VARCHAR(255)           NULL DEFAULT NULL,
    `gender`          VARCHAR(255)           NULL DEFAULT NULL,
    `name`            VARCHAR(255)           NULL DEFAULT NULL,
    `password`        VARCHAR(255)           NULL DEFAULT NULL,
    `patronymic`      VARCHAR(255)           NULL DEFAULT NULL,
    `phone_number`    VARCHAR(255)           NULL DEFAULT NULL,
    `role`            ENUM ('USER', 'ADMIN') NULL DEFAULT NULL,
    `surname`         VARCHAR(255)           NULL DEFAULT NULL,
    `passport_number` VARCHAR(255)           NULL DEFAULT NULL,
    `passport_series` VARCHAR(255)           NULL DEFAULT NULL,
    `birthday`        DATETIME(6)            NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
     ;

CREATE TABLE IF NOT EXISTS `touragency`.`orders`
(
    `id`                 BIGINT       NOT NULL AUTO_INCREMENT,
    `created_date`       DATETIME     NULL DEFAULT NULL,
    `date`               DATE         NULL DEFAULT NULL,
    `name_of_tour`       VARCHAR(255) NULL DEFAULT NULL,
    `number_of_days`     INT          NULL DEFAULT NULL,
    `number_of_people`   INT          NULL DEFAULT NULL,
    `special_request`    VARCHAR(255) NULL DEFAULT NULL,
    `status`             VARCHAR(255) NULL DEFAULT NULL,
    `update_status_date` VARCHAR(255) NULL DEFAULT NULL,
    `end_date`           DATE         NULL DEFAULT NULL,
    `tour_id`            BIGINT       NULL DEFAULT NULL,
    `users_id`           BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_orders_users1_idx` (`users_id` ASC) ,
    CONSTRAINT `fk_orders_users1`
        FOREIGN KEY (`users_id`)
            REFERENCES `touragency`.`users` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
     ;

CREATE TABLE IF NOT EXISTS `touragency`.`tours`
(
    `id`             BIGINT       NOT NULL AUTO_INCREMENT,
    `start_date`     DATETIME(6)  NULL DEFAULT NULL,
    `country`        VARCHAR(255) NULL DEFAULT NULL,
    `description`    LONGTEXT     NULL DEFAULT NULL,
    `end_date`       DATETIME(6)  NULL DEFAULT NULL,
    `location`       VARCHAR(255) NULL DEFAULT NULL,
    `name`           VARCHAR(255) NULL DEFAULT NULL,
    `number_of_days` INT          NULL DEFAULT NULL,
    `price`          DOUBLE       NULL DEFAULT NULL,
    `program`        VARCHAR(255) NULL DEFAULT NULL,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
     ;

CREATE TABLE IF NOT EXISTS `touragency`.`orders_has_languages`
(
    `orders_id`    BIGINT NOT NULL,
    `languages_id` INT    NOT NULL,
    PRIMARY KEY (`orders_id`, `languages_id`),
    INDEX `fk_orders_has_languages_languages1_idx` (`languages_id` ASC) ,
    INDEX `fk_orders_has_languages_orders_idx` (`orders_id` ASC) ,
    CONSTRAINT `fk_orders_has_languages_orders`
        FOREIGN KEY (`orders_id`)
            REFERENCES `touragency`.`orders` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_orders_has_languages_languages1`
        FOREIGN KEY (`languages_id`)
            REFERENCES `touragency`.`languages` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
     ;

CREATE TABLE IF NOT EXISTS `touragency`.`tours_has_languages`
(
    `tours_id`     BIGINT NOT NULL,
    `languages_id` INT    NOT NULL,
    PRIMARY KEY (`tours_id`, `languages_id`),
    INDEX `fk_tours_has_languages_languages1_idx` (`languages_id` ASC) ,
    INDEX `fk_tours_has_languages_tours1_idx` (`tours_id` ASC) ,
    CONSTRAINT `fk_tours_has_languages_tours1`
        FOREIGN KEY (`tours_id`)
            REFERENCES `touragency`.`tours` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `fk_tours_has_languages_languages1`
        FOREIGN KEY (`languages_id`)
            REFERENCES `touragency`.`languages` (`id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4
     ;

CREATE TABLE IF NOT EXISTS `touragency`.`users_favorite_tours`
(
    `user_id`      BIGINT NOT NULL,
    `tour_id`      BIGINT NOT NULL,
    `created_date` DATETIME DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`user_id`, `tour_id`),
    INDEX `fk_users_favorite_tours_users_idx` (`user_id` ASC),
    INDEX `fk_users_favorite_tours_tours_idx` (`tour_id` ASC),
    CONSTRAINT `fk_users_favorite_tours_users`
        FOREIGN KEY (`user_id`)
            REFERENCES `touragency`.`users` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT `fk_users_favorite_tours_tours`
        FOREIGN KEY (`tour_id`)
            REFERENCES `touragency`.`tours` (`id`)
            ON DELETE CASCADE
            ON UPDATE CASCADE
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

