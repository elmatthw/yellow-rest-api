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
DROP TABLE IF EXISTS `yellow`.`User` ;

CREATE TABLE IF NOT EXISTS `yellow`.`User` (
                                             `USER_ID` BIGINT NOT NULL AUTO_INCREMENT,
                                             `email` VARCHAR(45) NOT NULL,
                                             `username` VARCHAR(45) NOT NULL,
                                             `password` VARCHAR(300) NOT NULL,
                                             `confirmPassword` VARCHAR(300),
                                             PRIMARY KEY (`USER_ID`),
                                             UNIQUE INDEX `USER_ID_UNIQUE` (`USER_ID` ASC) VISIBLE,
                                             UNIQUE INDEX `email_UNIQUE` (`email` ASC) VISIBLE,
                                             UNIQUE INDEX `username_UNIQUE` (`username` ASC) VISIBLE)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `yellow`.`WeeklyReport`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`WeeklyReport` ;

CREATE TABLE IF NOT EXISTS `yellow`.`WeeklyReport` (
                                                     `WEEKLYREPORT_ID` BIGINT NOT NULL AUTO_INCREMENT,
                                                     `weekNumber` INT,
                                                     `averageSpeed` DOUBLE NOT NULL,
                                                     `averageTime` VARCHAR(45) NOT NULL,
                                                     `totalDistance` DOUBLE NOT NULL,
                                                     `User_USER_ID` BIGINT,
                                                     `startDate` TIMESTAMP NOT NULL,
                                                     `endDate` TIMESTAMP NOT NULL,
                                                     PRIMARY KEY (`WEEKLYREPORT_ID`),
                                                     INDEX `fk_WeeklyReport_User1_idx` (`User_USER_ID` ASC) VISIBLE,
                                                     CONSTRAINT `fk_WeeklyReport_User1`
                                                       FOREIGN KEY (`User_USER_ID`)
                                                         REFERENCES `yellow`.`User` (`USER_ID`)
                                                         ON DELETE NO ACTION
                                                         ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `yellow`.`Running`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`Running` ;

CREATE TABLE IF NOT EXISTS `yellow`.`Running` (
                                                `RUNNING_ID` BIGINT NOT NULL AUTO_INCREMENT,
                                                `distance` DOUBLE NOT NULL,
                                                `startTime` TIMESTAMP,
                                                `finishTime` TIMESTAMP,
                                                `User_USER_ID` BIGINT,
                                                `WeeklyReport_WEEKLYREPORT_ID` BIGINT,
                                                PRIMARY KEY (`RUNNING_ID`),
                                                INDEX `fk_Running_User_idx` (`User_USER_ID` ASC) VISIBLE,
                                                INDEX `fk_Running_WeeklyReport1_idx` (`WeeklyReport_WEEKLYREPORT_ID` ASC) VISIBLE,
                                                CONSTRAINT `fk_Running_User`
                                                  FOREIGN KEY (`User_USER_ID`)
                                                    REFERENCES `yellow`.`User` (`USER_ID`)
                                                    ON DELETE NO ACTION
                                                    ON UPDATE NO ACTION,
                                                CONSTRAINT `fk_Running_WeeklyReport1`
                                                  FOREIGN KEY (`WeeklyReport_WEEKLYREPORT_ID`)
                                                    REFERENCES `yellow`.`WeeklyReport` (`WEEKLYREPORT_ID`)
                                                    ON DELETE NO ACTION
                                                    ON UPDATE NO ACTION)
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `yellow`.`Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`Role` ;

CREATE TABLE IF NOT EXISTS `yellow`.`Role` (
                                             `ROLE_ID` BIGINT NOT NULL,
                                             `name` VARCHAR(45) NOT NULL,
                                             PRIMARY KEY (`ROLE_ID`))
  ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `yellow`.`User_Role`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `yellow`.`User_Role` ;

CREATE TABLE IF NOT EXISTS `yellow`.`User_Role` (
                                                  `User_USER_ID` BIGINT NOT NULL,
                                                  `Role_ROLE_ID` BIGINT NOT NULL,
                                                  INDEX `fk_User_Role_User1_idx` (`User_USER_ID` ASC) VISIBLE,
                                                  INDEX `fk_User_Role_Role1_idx` (`Role_ROLE_ID` ASC) VISIBLE,
                                                  CONSTRAINT `fk_User_Role_User1`
                                                    FOREIGN KEY (`User_USER_ID`)
                                                      REFERENCES `yellow`.`User` (`USER_ID`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION,
                                                  CONSTRAINT `fk_User_Role_Role1`
                                                    FOREIGN KEY (`Role_ROLE_ID`)
                                                      REFERENCES `yellow`.`Role` (`ROLE_ID`)
                                                      ON DELETE NO ACTION
                                                      ON UPDATE NO ACTION)
  ENGINE = InnoDB;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `yellow`.`User`
-- -----------------------------------------------------
START TRANSACTION;
USE `yellow`;
INSERT INTO `yellow`.`User` (`USER_ID`, `email`, `username`, `password`) VALUES (1, 'elmatthw8@gmail.com', 'admin', 'admin');

COMMIT;


-- -----------------------------------------------------
-- Data for table `yellow`.`Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `yellow`;
INSERT INTO `yellow`.`Role` (`ROLE_ID`, `name`) VALUES (1, 'admin');
INSERT INTO `yellow`.`Role` (`ROLE_ID`, `name`) VALUES (2, 'user');

COMMIT;


-- -----------------------------------------------------
-- Data for table `yellow`.`User_Role`
-- -----------------------------------------------------
START TRANSACTION;
USE `yellow`;
INSERT INTO `yellow`.`User_Role` (`User_USER_ID`, `Role_ROLE_ID`) VALUES (1, 1);

COMMIT;

