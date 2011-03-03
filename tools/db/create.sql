SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL';

DROP SCHEMA IF EXISTS `library` ;
CREATE SCHEMA IF NOT EXISTS `library` ;
USE `library` ;

-- -----------------------------------------------------
-- Table `library`.`book`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `library`.`book` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `bar_code` CHAR(11) NOT NULL ,
  `isbn` VARCHAR(50) NOT NULL ,
  `name` VARCHAR(100) NOT NULL ,
  `series` VARCHAR(100) NULL COMMENT '丛书名' ,
  `volume` VARCHAR(100) NULL COMMENT '分册号' ,
  `publisher` VARCHAR(255) NOT NULL ,
  `author` VARCHAR(100) NOT NULL ,
  `translator` VARCHAR(100) NULL ,
  `call_num` VARCHAR(45) NOT NULL ,
  `status` TINYINT(2) NOT NULL DEFAULT 0 ,
  `lent_num` INT NOT NULL DEFAULT 0 ,
  `classifier` VARCHAR(255) NULL COMMENT '分类号' ,
  `pub_date` DATE NULL COMMENT '出版日期' ,
  `price` DOUBLE NULL ,
  `edition` INT NULL DEFAULT 1 COMMENT '版次' ,
  `page_num` INT NULL DEFAULT 0 COMMENT '页数' ,
  `remarks` VARCHAR(255) NULL COMMENT '备注' ,
  `summary` VARCHAR(255) NULL COMMENT '内容简介' ,
  `enter_date` DATE NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `bar_code_UNIQUE` (`bar_code` ASC) ,
  INDEX `namde` (`name` ASC) ,
  INDEX `author` (`author` ASC) ,
  INDEX `call_num` (`call_num` ASC) ,
  INDEX `isbn` (`isbn` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`reader_type`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `library`.`reader_type` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `name` VARCHAR(50) NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`reader`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `library`.`reader` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `type_id` INT NOT NULL ,
  `bar_code` VARCHAR(45) NOT NULL ,
  `name` VARCHAR(20) NOT NULL ,
  `identity_num` CHAR(18) NULL ,
  `sex` TINYINT(2) NOT NULL DEFAULT 0 ,
  `phone` VARCHAR(50) NULL ,
  `email` VARCHAR(50) NULL ,
  `status` TINYINT(2) NOT NULL DEFAULT 0 ,
  `photo` LONGBLOB NULL ,
  `remarks` VARCHAR(255) NULL ,
  `date` DATE NOT NULL ,
  PRIMARY KEY (`id`) ,
  INDEX `type` (`type_id` ASC) ,
  UNIQUE INDEX `bar_code_UNIQUE` (`bar_code` ASC) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `name` (`name` ASC) ,
  INDEX `date` (`date` ASC) ,
  CONSTRAINT `type`
    FOREIGN KEY (`type_id` )
    REFERENCES `library`.`reader_type` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`bbr`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `library`.`bbr` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `book_id` INT NOT NULL ,
  `reader_id` INT NOT NULL ,
  `returned` TINYINT(1)  NOT NULL DEFAULT false ,
  `bdate` DATETIME NOT NULL ,
  `rdate` DATETIME NULL ,
  INDEX `book` (`book_id` ASC) ,
  INDEX `reader` (`reader_id` ASC) ,
  INDEX `bdate` (`bdate` ASC) ,
  PRIMARY KEY (`id`) ,
  CONSTRAINT `book`
    FOREIGN KEY (`book_id` )
    REFERENCES `library`.`book` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `reader`
    FOREIGN KEY (`reader_id` )
    REFERENCES `library`.`reader` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `library`.`user`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `library`.`user` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `username` VARCHAR(45) NOT NULL ,
  `password` CHAR(32) NOT NULL ,
  `type` TINYINT(3) NOT NULL DEFAULT 0 ,
  `date` DATE NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  UNIQUE INDEX `username_UNIQUE` (`username` ASC) ,
  INDEX `date` (`date` ASC) )
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `library`.`ri`
-- -----------------------------------------------------
CREATE  TABLE IF NOT EXISTS `library`.`ri` (
  `id` INT NOT NULL AUTO_INCREMENT ,
  `reader_id` INT NOT NULL ,
  `datetime` DATETIME NOT NULL ,
  PRIMARY KEY (`id`) ,
  UNIQUE INDEX `id_UNIQUE` (`id` ASC) ,
  INDEX `readerid` (`reader_id` ASC) ,
  CONSTRAINT `readerid`
    FOREIGN KEY (`reader_id` )
    REFERENCES `library`.`reader` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8;



SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;

-- -----------------------------------------------------
-- Data for table `library`.`reader_type`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `library`;
INSERT INTO `library`.`reader_type` (`id`, `name`) VALUES ('1', '普通读者');
INSERT INTO `library`.`reader_type` (`id`, `name`) VALUES ('2', '高级读者');
INSERT INTO `library`.`reader_type` (`id`, `name`) VALUES ('3', 'VIP读者');

COMMIT;

-- -----------------------------------------------------
-- Data for table `library`.`user`
-- -----------------------------------------------------
SET AUTOCOMMIT=0;
USE `library`;
INSERT INTO `library`.`user` (`id`, `username`, `password`, `type`, `date`) VALUES ('1', 'admin', '21232F297A57A5A743894A0E4A801FC3', '2', '2011-01-03');

COMMIT;
