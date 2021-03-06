-- MySQL Script generated by MySQL Workbench
-- Mon 29 Aug 2016 12:12:33 AM MSK
-- Model: New Model    Version: 1.0
-- MySQL Workbench Forward Engineering

-- -----------------------------------------------------
-- Schema airline
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `airline` DEFAULT CHARACTER SET utf8 ;
USE `airline` ;

-- -----------------------------------------------------
-- Table `airline`.`country`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`country` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `long_name` VARCHAR(45) NULL,
  `short_name` CHAR(2) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `name_UNIQUE` (`long_name` ASC),
  UNIQUE INDEX `short_name_UNIQUE` (`short_name` ASC))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`city`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`city` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(255) NOT NULL,
  `country_id` INT NOT NULL,
  `place_id` VARCHAR(255) BINARY NOT NULL,
  `latitude` DOUBLE NOT NULL,
  `longitude` DOUBLE NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_city_country_idx` (`country_id` ASC),
  UNIQUE INDEX `place_id_UNIQUE` (`place_id` ASC),
  CONSTRAINT `fk_city_country`
    FOREIGN KEY (`country_id`)
    REFERENCES `airline`.`country` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`hub`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`hub` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `city_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_hub_city1`
    FOREIGN KEY (`city_id`)
    REFERENCES `airline`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`route`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`route` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `hub_id` INT NOT NULL,
  `city_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_routes_hub1_idx` (`hub_id` ASC),
  INDEX `fk_destinations_city1_idx` (`city_id` ASC),
  CONSTRAINT `fk_routes_hub1`
    FOREIGN KEY (`hub_id`)
    REFERENCES `airline`.`hub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
   CONSTRAINT `fk_destinations_city1`
    FOREIGN KEY (`city_id`)
    REFERENCES `airline`.`city` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`airplane_model`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`airplane_model` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `flight_range` INT NULL,
  `avg_speed` INT NOT NULL,
  `pilots` INT NOT NULL,
  `navigators` INT NOT NULL,
  `radio_operators` INT NOT NULL,
  `air_stewards` INT NOT NULL,
  PRIMARY KEY (`id`))
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`flight`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`flight` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `route_id` INT NOT NULL,
  `airplane_model_id` INT NOT NULL,
  `dept_time` DATETIME NOT NULL,
  `dept_time_back` DATETIME NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_flight_route1_idx` (`route_id` ASC),
  INDEX `fk_flight_airplane_model1_idx` (`airplane_model_id` ASC),
  CONSTRAINT `fk_flight_route1`
    FOREIGN KEY (`route_id`)
    REFERENCES `airline`.`route` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_flight_airplane_model1`
    FOREIGN KEY (`airplane_model_id`)
    REFERENCES `airline`.`airplane_model` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`employee`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`employee` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `first_name` VARCHAR(45) NULL,
  `last_name` VARCHAR(45) NULL,
  `specialty` ENUM('pilot', 'navigator', 'radio_operator', 'air_steward') NULL,
  `hub_id` INT NOT NULL,
  PRIMARY KEY (`id`),
  INDEX `fk_employee_hub1_idx` (`hub_id` ASC),
  CONSTRAINT `fk_employee_hub1`
    FOREIGN KEY (`hub_id`)
    REFERENCES `airline`.`hub` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`crew`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`crew` (
  `flight_id` INT NOT NULL,
  `employee_id` INT NOT NULL,
  INDEX `fk_crew_flight1_idx` (`flight_id` ASC),
  INDEX `fk_crew_employee1_idx` (`employee_id` ASC),
  CONSTRAINT `fk_crew_flight1`
    FOREIGN KEY (`flight_id`)
    REFERENCES `airline`.`flight` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_crew_employee1`
    FOREIGN KEY (`employee_id`)
    REFERENCES `airline`.`employee` (`id`)
    ON DELETE CASCADE
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `airline`.`user`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `airline`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `login` VARCHAR(45) NOT NULL,
  `password_sha` CHAR(64) NOT NULL,
  `first_name` VARCHAR(25) NULL,
  `last_name` VARCHAR(25) NULL,
  `type` ENUM('admin', 'dispatcher', 'root') NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `login_UNIQUE` (`login` ASC))
ENGINE = InnoDB;