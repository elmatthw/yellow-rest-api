
CREATE TABLE `user` (
                                               `user_id` BIGINT NOT NULL AUTO_INCREMENT,
                                               `email` VARCHAR(45) NOT NULL,
                                               `username` VARCHAR(45) NOT NULL,
                                               `password` VARCHAR(300) NOT NULL,
                                               `confirm_password` VARCHAR(300),
                                               PRIMARY KEY (`user_id`),
                                               UNIQUE INDEX `USER_ID_UNIQUE` (`user_id` ASC) VISIBLE,
                                               UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
                                               UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
    ENGINE = InnoDB;

CREATE TABLE `running` (
                                                  `running_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                  `distance` DOUBLE NOT NULL,
                                                  `start_time` TIMESTAMP,
                                                  `finish_Time` TIMESTAMP,
                                                  `user_user_id` BIGINT,
                                                  PRIMARY KEY (`running_id`),
                                                  INDEX `fk_running_user_idx` (`user_user_id` ASC) VISIBLE,
                                                  CONSTRAINT `fk_running_user`
                                                      FOREIGN KEY (`user_user_id`)
                                                          REFERENCES `user` (`user_id`)
                                                          ON DELETE NO ACTION
                                                          ON UPDATE NO ACTION)
ENGINE = InnoDB;

CREATE TABLE `role` (
                                               `role_id` BIGINT NOT NULL,
                                               `name` VARCHAR(45) NOT NULL,
                                               PRIMARY KEY (`role_id`))
    ENGINE = InnoDB;

CREATE TABLE `user_role` (
                                                    `user_user_id` BIGINT NOT NULL,
                                                    `role_role_id` BIGINT NOT NULL,
                                                    INDEX `fk_user_role_user1_idx` (`user_user_id` ASC) VISIBLE,
                                                    INDEX `fk_user_role_role1_idx` (`role_role_id` ASC) VISIBLE,
                                                    CONSTRAINT `fk_user_role_user1`
                                                        FOREIGN KEY (`user_user_id`)
                                                            REFERENCES `user` (`user_id`)
                                                            ON DELETE NO ACTION
                                                            ON UPDATE NO ACTION,
                                                    CONSTRAINT `fk_user_role_role1`
                                                        FOREIGN KEY (`role_role_id`)
                                                            REFERENCES `role` (`role_id`)
                                                            ON DELETE NO ACTION
                                                            ON UPDATE NO ACTION)
    ENGINE = InnoDB;

INSERT INTO `user` (`user_id`, `email`, `username`, `password`) VALUES (1, 'elmatthw8@gmail.com', 'admin', 'admin');
INSERT INTO `user` (`user_id`, `email`, `username`, `password`) VALUES (2, 'email@email.com', 'username', '$2a$10$Edt2Kg22IpiyDLDNPdIsE./dS25qntbLXG.SuIgvhR/sqfsS6JmOK');
INSERT INTO `role` (`role_id`, `name`) VALUES (1, 'admin');
INSERT INTO `role` (`role_id`, `name`) VALUES (2, 'user');
INSERT INTO `user_role` (`user_user_id`, `role_role_id`) VALUES (1, 1);
INSERT INTO `user_role` (`user_user_id`, `role_role_id`) VALUES (2, 2);


COMMIT;

