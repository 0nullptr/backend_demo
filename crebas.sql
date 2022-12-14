-- Active: 1666756892821@@127.0.0.1@3306@dishmanagedatabase

USE dishmanagedatabase;

SET NAMES utf8mb4;

SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `box`;

CREATE TABLE
    `box` (
        `BoxID` BIGINT NOT NULL AUTO_INCREMENT,
        `WindowID` BIGINT NOT NULL,
        `PositionNumber` INT NOT NULL,
        PRIMARY KEY(`BoxID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `buy`;

CREATE TABLE
    `buy` (
        `BuyID` BIGINT NOT NULL AUTO_INCREMENT,
        `StudentID` BIGINT NOT NULL,
        `ContainID` BIGINT NOT NULL,
        PRIMARY KEY(`BuyID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `contain`;

CREATE TABLE
    `contain` (
        `ContainID` BIGINT NOT NULL AUTO_INCREMENT,
        `BoxID` BIGINT NOT NULL,
        `DishID` BIGINT NOT NULL,
        `SellDate` DATE NULL DEFAULT NULL,
        `SellMeal` INT NULL DEFAULT NULL,
        PRIMARY KEY(`ContainID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `dish`;

CREATE TABLE
    `dish` (
        `DishID` BIGINT NOT NULL AUTO_INCREMENT,
        `DishName` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
        `DishValue` DECIMAL(5, 2) NOT NULL,
        `SchoolID` BIGINT NOT NULL,
        PRIMARY KEY(`DishID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `parent`;

CREATE TABLE
    `parent` (
        `ParentID` BIGINT NOT NULL AUTO_INCREMENT,
        `UnionID` VARCHAR(29) NULL DEFAULT NULL,
        PRIMARY KEY(`ParentID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `school`;

CREATE TABLE
    `school` (
        `SchoolID` BIGINT NOT NULL AUTO_INCREMENT,
        `SchoolName` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
        `WindowCount` INT NULL DEFAULT 0,
        PRIMARY KEY(`SchoolID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `staff`;

CREATE TABLE
    `staff` (
        `StaffID` BIGINT NOT NULL AUTO_INCREMENT,
        `UnionID` VARCHAR(29) NULL DEFAULT NULL,
        `SchoolID` BIGINT NULL,
        PRIMARY KEY(`StaffID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `student`;

CREATE TABLE
    `student` (
        `StudentID` BIGINT NOT NULL AUTO_INCREMENT,
        `StudentName` VARCHAR(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL,
        `SchoolID` BIGINT NOT NULL,
        `ParentID` BIGINT NULL DEFAULT NULL,
        `CardID` VARCHAR(64) NOT NULL,
        PRIMARY KEY(`StudentID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `windows`;

CREATE TABLE
    `windows` (
        `WindowID` BIGINT NOT NULL AUTO_INCREMENT,
        `SchoolID` BIGINT NOT NULL,
        `BoxCount` INT NULL DEFAULT 0,
        PRIMARY KEY(`WindowID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

DROP TABLE IF EXISTS `image`;

CREATE TABLE
    `image` (
        `ImageID` BIGINT NOT NULL AUTO_INCREMENT,
        `DishID` BIGINT NULL DEFAULT NULL,
        `Path` VARCHAR(36) NOT NULL,
        PRIMARY KEY(`ImageID`) USING BTREE
    ) ENGINE = InnoDB CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = DYNAMIC;

INSERT INTO
    `school` (`SchoolName`, `WindowCount`)
VALUES('杭州电子科技大学', 1);

INSERT INTO `windows` (`SchoolID`, `BoxCount`) VALUES(1, 6);

INSERT INTO
    `box` (`WindowID`, `PositionNumber`)
VALUES (1, 1), (1, 2), (1, 3), (1, 4), (1, 5), (1, 6);

INSERT INTO
    `student`(
        `StudentName`,
        `SchoolID`,
        `CardID`
    )
VALUES ('张三', 1, '9CDFC07C'), ('张四', 2, '7DA4FCAF');