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
    `active`          BIT(1)                 NOT NULL DEFAULT b'1',
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
    `balance`         DOUBLE                 NOT NULL DEFAULT 1000,
    PRIMARY KEY (`id`)
)
    ENGINE = InnoDB
    DEFAULT CHARACTER SET = utf8mb4;

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
    `update_status_date` DATETIME     NULL DEFAULT NULL,
    `end_date`           DATE         NULL DEFAULT NULL,
    `tour_id`            BIGINT       NULL DEFAULT NULL,
    `users_id`           BIGINT       NOT NULL,
    PRIMARY KEY (`id`),
    INDEX `fk_orders_users1_idx` (`users_id` ASC),
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
    DEFAULT CHARACTER SET = utf8mb4;

CREATE TABLE IF NOT EXISTS `touragency`.`orders_has_languages`
(
    `orders_id`    BIGINT NOT NULL,
    `languages_id` INT    NOT NULL,
    PRIMARY KEY (`orders_id`, `languages_id`),
    INDEX `fk_orders_has_languages_languages1_idx` (`languages_id` ASC),
    INDEX `fk_orders_has_languages_orders_idx` (`orders_id` ASC),
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
    INDEX `fk_tours_has_languages_languages1_idx` (`languages_id` ASC),
    INDEX `fk_tours_has_languages_tours1_idx` (`tours_id` ASC),
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

INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-06-01 00:00:00.000000', 'France', 'A wonderful tour to Bangkok, France.', '2025-06-07 00:00:00.000000',
        'Bangkok', 'Adventure in Bangkok', 6, 2240.68, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-21 00:00:00.000000', 'Italy', 'A wonderful tour to Tokyo, Italy.', '2025-05-31 00:00:00.000000',
        'Tokyo', 'Unforgettable trip to Tokyo', 10, 1138.01, 'Beach vacation');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-29 00:00:00.000000', 'Germany', 'A wonderful tour to Paris, Germany.', '2025-10-09 00:00:00.000000',
        'Paris', 'Discover Paris', 10, 4008.15, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-13 00:00:00.000000', 'USA', 'A wonderful tour to Tokyo, USA.', '2025-07-17 00:00:00.000000',
        'Tokyo', 'Unforgettable trip to Tokyo', 4, 396.25, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-22 00:00:00.000000', 'Spain', 'A wonderful tour to New York, Spain.', '2025-07-26 00:00:00.000000',
        'New York', 'Escape to New York', 4, 1191.48, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-06-28 00:00:00.000000', 'Japan', 'A wonderful tour to Athens, Japan.', '2025-07-12 00:00:00.000000',
        'Athens', 'Adventure in Athens', 14, 4940.84, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-29 00:00:00.000000', 'Thailand', 'A wonderful tour to Bangkok, Thailand.',
        '2025-10-13 00:00:00.000000',
        'Bangkok', 'Escape to Bangkok', 14, 3427.55, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-06 00:00:00.000000', 'France', 'A wonderful tour to Bangkok, France.', '2025-05-16 00:00:00.000000',
        'Bangkok', 'Adventure in Bangkok', 10, 2151.44, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-16 00:00:00.000000', 'France', 'A wonderful tour to Rome, France.', '2025-07-29 00:00:00.000000',
        'Rome', 'Unforgettable trip to Rome', 13, 2820.05, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-24 00:00:00.000000', 'Italy', 'A wonderful tour to Barcelona, Italy.', '2025-06-03 00:00:00.000000',
        'Barcelona', 'Adventure in Barcelona', 10, 4361.08, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-30 00:00:00.000000', 'Thailand', 'A wonderful tour to Paris, Thailand.', '2025-09-06 00:00:00.000000',
        'Paris', 'Adventure in Paris', 7, 1768.77, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-20 00:00:00.000000', 'Germany', 'A wonderful tour to Athens, Germany.', '2025-10-30 00:00:00.000000',
        'Athens', 'Escape to Athens', 10, 1379.41, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-04 00:00:00.000000', 'Turkey', 'A wonderful tour to Berlin, Turkey.', '2025-07-16 00:00:00.000000',
        'Berlin', 'Unforgettable trip to Berlin', 12, 3103.14, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-03 00:00:00.000000', 'Thailand', 'A wonderful tour to Bangkok, Thailand.',
        '2025-10-17 00:00:00.000000',
        'Bangkok', 'Escape to Bangkok', 14, 4293.08, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-05 00:00:00.000000', 'France', 'A wonderful tour to Paris, France.', '2025-09-09 00:00:00.000000',
        'Paris', 'Adventure in Paris', 4, 4604.21, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-02 00:00:00.000000', 'Turkey', 'A wonderful tour to Bangkok, Turkey.', '2025-08-15 00:00:00.000000',
        'Bangkok', 'Unforgettable trip to Bangkok', 13, 536.73, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-19 00:00:00.000000', 'Greece', 'A wonderful tour to Istanbul, Greece.', '2025-07-31 00:00:00.000000',
        'Istanbul', 'Adventure in Istanbul', 12, 2767.21, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-08 00:00:00.000000', 'USA', 'A wonderful tour to Tokyo, USA.', '2025-09-20 00:00:00.000000',
        'Tokyo', 'Escape to Tokyo', 12, 590.13, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-05 00:00:00.000000', 'Norway', 'A wonderful tour to Bangkok, Norway.', '2025-07-12 00:00:00.000000',
        'Bangkok', 'Escape to Bangkok', 7, 2438.36, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-17 00:00:00.000000', 'Italy', 'A wonderful tour to Rome, Italy.', '2025-05-30 00:00:00.000000',
        'Rome', 'Explore Rome', 13, 2516.23, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-06-22 00:00:00.000000', 'Norway', 'A wonderful tour to Athens, Norway.', '2025-07-06 00:00:00.000000',
        'Athens', 'Explore Athens', 14, 4043.02, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-20 00:00:00.000000', 'France', 'A wonderful tour to Bangkok, France.', '2025-10-03 00:00:00.000000',
        'Bangkok', 'Escape to Bangkok', 13, 2348.16, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-06-14 00:00:00.000000', 'Japan', 'A wonderful tour to Paris, Japan.', '2025-06-25 00:00:00.000000',
        'Paris', 'Escape to Paris', 11, 2624.32, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-02 00:00:00.000000', 'Germany', 'A wonderful tour to Barcelona, Germany.',
        '2025-05-07 00:00:00.000000',
        'Barcelona', 'Explore Barcelona', 5, 3713.69, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-30 00:00:00.000000', 'France', 'A wonderful tour to Paris, France.', '2025-08-08 00:00:00.000000',
        'Paris', 'Adventure in Paris', 9, 428.08, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-23 00:00:00.000000', 'Japan', 'A wonderful tour to Oslo, Japan.', '2025-07-29 00:00:00.000000',
        'Oslo', 'Discover Oslo', 6, 3031.77, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-24 00:00:00.000000', 'Japan', 'A wonderful tour to New York, Japan.', '2025-09-28 00:00:00.000000',
        'New York', 'Unforgettable trip to New York', 4, 594.87, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-23 00:00:00.000000', 'France', 'A wonderful tour to Athens, France.', '2025-10-31 00:00:00.000000',
        'Athens', 'Adventure in Athens', 8, 1406.8, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-25 00:00:00.000000', 'Italy', 'A wonderful tour to Paris, Italy.', '2025-09-06 00:00:00.000000',
        'Paris', 'Discover Paris', 12, 2392.37, 'Beach vacation');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-21 00:00:00.000000', 'Japan', 'A wonderful tour to Tokyo, Japan.', '2025-08-30 00:00:00.000000',
        'Tokyo', 'Adventure in Tokyo', 9, 1950.22, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-06-16 00:00:00.000000', 'Germany', 'A wonderful tour to Rome, Germany.', '2025-06-24 00:00:00.000000',
        'Rome', 'Discover Rome', 8, 3389.31, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-04 00:00:00.000000', 'Spain', 'A wonderful tour to Barcelona, Spain.', '2025-07-18 00:00:00.000000',
        'Barcelona', 'Escape to Barcelona', 14, 3259.98, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-11 00:00:00.000000', 'Spain', 'A wonderful tour to Tokyo, Spain.', '2025-10-14 00:00:00.000000',
        'Tokyo', 'Unforgettable trip to Tokyo', 3, 507.83, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-06 00:00:00.000000', 'Spain', 'A wonderful tour to Berlin, Spain.', '2025-09-12 00:00:00.000000',
        'Berlin', 'Discover Berlin', 6, 4267.22, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-14 00:00:00.000000', 'Spain', 'A wonderful tour to Paris, Spain.', '2025-07-26 00:00:00.000000',
        'Paris', 'Discover Paris', 12, 3849.54, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-05 00:00:00.000000', 'Greece', 'A wonderful tour to Istanbul, Greece.', '2025-08-11 00:00:00.000000',
        'Istanbul', 'Explore Istanbul', 6, 3997.71, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-10 00:00:00.000000', 'Spain', 'A wonderful tour to Paris, Spain.', '2025-08-17 00:00:00.000000',
        'Paris', 'Discover Paris', 7, 2534.29, 'Beach vacation');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-12 00:00:00.000000', 'France', 'A wonderful tour to Bangkok, France.', '2025-10-25 00:00:00.000000',
        'Bangkok', 'Escape to Bangkok', 13, 4742.27, 'Beach vacation');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-04 00:00:00.000000', 'Greece', 'A wonderful tour to Rome, Greece.', '2025-10-13 00:00:00.000000',
        'Rome', 'Adventure in Rome', 9, 2537.88, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-27 00:00:00.000000', 'Japan', 'A wonderful tour to Rome, Japan.', '2025-10-08 00:00:00.000000',
        'Rome', 'Adventure in Rome', 11, 884.38, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-10-09 00:00:00.000000', 'USA', 'A wonderful tour to Bangkok, USA.', '2025-10-23 00:00:00.000000',
        'Bangkok', 'Escape to Bangkok', 14, 1308.9, 'Mountain hiking');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-06-12 00:00:00.000000', 'Italy', 'A wonderful tour to Barcelona, Italy.', '2025-06-16 00:00:00.000000',
        'Barcelona', 'Discover Barcelona', 4, 3562.08, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-13 00:00:00.000000', 'Greece', 'A wonderful tour to Oslo, Greece.', '2025-05-20 00:00:00.000000',
        'Oslo', 'Unforgettable trip to Oslo', 7, 4622.09, 'Beach vacation');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-08-20 00:00:00.000000', 'Norway', 'A wonderful tour to Bangkok, Norway.', '2025-08-27 00:00:00.000000',
        'Bangkok', 'Discover Bangkok', 7, 1744.16, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-25 00:00:00.000000', 'Germany', 'A wonderful tour to Barcelona, Germany.',
        '2025-06-05 00:00:00.000000',
        'Barcelona', 'Explore Barcelona', 11, 1570.03, 'City tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-15 00:00:00.000000', 'Greece', 'A wonderful tour to Athens, Greece.', '2025-05-23 00:00:00.000000',
        'Athens', 'Escape to Athens', 8, 1863.04, 'Beach vacation');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-07-08 00:00:00.000000', 'Germany', 'A wonderful tour to Oslo, Germany.', '2025-07-18 00:00:00.000000',
        'Oslo', 'Explore Oslo', 10, 1667.75, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-05-10 00:00:00.000000', 'France', 'A wonderful tour to Paris, France.', '2025-05-15 00:00:00.000000',
        'Paris', 'Adventure in Paris', 5, 3713.27, 'Cultural experience');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-24 00:00:00.000000', 'Japan', 'A wonderful tour to Oslo, Japan.', '2025-09-28 00:00:00.000000',
        'Oslo', 'Unforgettable trip to Oslo', 4, 1602.22, 'Food tour');
INSERT INTO `touragency`.`tours`
(`start_date`, `country`, `description`, `end_date`, `location`, `name`, `number_of_days`, `price`, `program`)
VALUES ('2025-09-01 00:00:00.000000', 'Turkey', 'A wonderful tour to New York, Turkey.', '2025-09-13 00:00:00.000000',
        'New York', 'Discover New York', 12, 4790.99, 'Mountain hiking');

INSERT INTO `touragency`.`languages` (`language`)
VALUES ('English'),
       ('French'),
       ('Spanish'),
       ('German'),
       ('Italian'),
       ('Japanese'),
       ('Chinese'),
       ('Russian');

INSERT INTO `touragency`.`tours_has_languages` (`tours_id`, `languages_id`)
VALUES (1, 1),
       (1, 2),
       (2, 3),
       (2, 5),
       (3, 4);
