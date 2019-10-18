-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema yellow
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `yellow` ;

-- -----------------------------------------------------
-- Schema yellow
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `yellow` DEFAULT CHARACTER SET utf8 ;
USE `yellow` ;

-- -----------------------------------------------------
-- Table `yellow`.`User`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`user` ;

CREATE TABLE IF NOT EXISTS `yellow`.`user` (
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


-- -----------------------------------------------------
-- Table `yellow`.`Running`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`running` ;

CREATE TABLE IF NOT EXISTS `yellow`.`running` (
                                                `running_id` BIGINT NOT NULL AUTO_INCREMENT,
                                                `distance` DOUBLE NOT NULL,
                                                `start_time` TIMESTAMP,
                                                `finish_Time` TIMESTAMP,
                                                `user_user_id` BIGINT,
                                                PRIMARY KEY (`running_id`),
                                                INDEX `fk_running_user_idx` (`user_user_id` ASC) VISIBLE,
                                                CONSTRAINT `fk_running_user`
                                                  FOREIGN KEY (`user_user_id`)
                                                    REFERENCES `yellow`.`user` (`user_id`)
                                                    ON DELETE NO ACTION
                                                    ON UPDATE NO ACTION
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `yellow`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`role` ;

CREATE TABLE IF NOT EXISTS `yellow`.`role` (
                                             `role_id` BIGINT NOT NULL,
                                             `name` VARCHAR(45) NOT NULL,
                                             PRIMARY KEY (`role_id`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `yellow`.`User_Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`user_role` ;

CREATE TABLE IF NOT EXISTS `yellow`.`user_role` (
                                                  `user_user_id` BIGINT NOT NULL,
                                                  `role_role_id` BIGINT NOT NULL,
                                                  INDEX `fk_user_role_user1_idx` (`user_user_id` ASC) VISIBLE,
                                                  INDEX `fk_user_role_role1_idx` (`role_role_id` ASC) VISIBLE,
                                                  CONSTRAINT `fk_user_role_user1`
                                                    FOREIGN KEY (`user_user_id`)
                                                      REFERENCES `yellow`.`user` (`user_id`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION,
                                                  CONSTRAINT `fk_user_role_role1`
                                                    FOREIGN KEY (`role_role_id`)
                                                      REFERENCES `yellow`.`role` (`role_id`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;



delimiter $$
drop function if exists first_date;
create function first_date() returns timestamp deterministic
begin
	declare result_date timestamp;
	select start_time from running order by start_time limit 1 into result_date;
    return result_date;
end $$

delimiter ;

-- -----------------------------------------------------
-- Data for table `yellow`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `yellow`;
INSERT INTO `yellow`.`user` (`user_id`, `email`, `username`, `password`) VALUES (1, 'elmatthw8@gmail.com', 'admin', 'admin');

COMMIT;


-- -----------------------------------------------------
-- Data for table `yellow`.`Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `yellow`;
INSERT INTO `yellow`.`role` (`role_id`, `name`) VALUES (1, 'admin');
INSERT INTO `yellow`.`role` (`role_id`, `name`) VALUES (2, 'user');

COMMIT;


-- -----------------------------------------------------
-- Data for table `yellow`.`User_Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `yellow`;
INSERT INTO `yellow`.`user_role` (`user_user_id`, `role_role_id`) VALUES (1, 1);

COMMIT;

