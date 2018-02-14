/*
Navicat MySQL Data Transfer

Source Server         : 172.18.7.160_3306
Source Server Version : 50718
Source Host           : 172.18.7.160:3306
Source Database       : sp_test_manage_bt

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-02-14 09:11:04
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for files
-- ----------------------------
DROP TABLE IF EXISTS `files`;
CREATE TABLE `files` (
`fileid`  int(10) NOT NULL AUTO_INCREMENT ,
`linkid`  int(10) NULL DEFAULT NULL ,
`linkfile`  longblob NULL ,
`linktype`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`fileid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=91

;

-- ----------------------------
-- Table structure for learn
-- ----------------------------
DROP TABLE IF EXISTS `learn`;
CREATE TABLE `learn` (
`learnid`  int(10) NOT NULL AUTO_INCREMENT ,
`learngroupid`  int(10) NOT NULL ,
`learnname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`learntext`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`inserttime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`learnid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=40

;

-- ----------------------------
-- Table structure for learn_type
-- ----------------------------
DROP TABLE IF EXISTS `learn_type`;
CREATE TABLE `learn_type` (
`learngroupid`  int(10) NOT NULL AUTO_INCREMENT ,
`learngroup`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`learngroupid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=12

;

-- ----------------------------
-- Table structure for project
-- ----------------------------
DROP TABLE IF EXISTS `project`;
CREATE TABLE `project` (
`projectid`  int(10) NOT NULL AUTO_INCREMENT ,
`projectname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`teamid`  int(10) NOT NULL ,
PRIMARY KEY (`projectid`, `projectname`, `teamid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=25

;

-- ----------------------------
-- Table structure for script
-- ----------------------------
DROP TABLE IF EXISTS `script`;
CREATE TABLE `script` (
`scriptid`  int(10) NOT NULL AUTO_INCREMENT ,
`xpath`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`testvalue`  longtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`scripttype`  int(10) NULL DEFAULT NULL ,
`scriptname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`step`  int(10) NULL DEFAULT NULL ,
`testid`  int(10) NULL DEFAULT NULL ,
`testurl`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`scriptid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=7652

;

-- ----------------------------
-- Table structure for seleniumfiles
-- ----------------------------
DROP TABLE IF EXISTS `seleniumfiles`;
CREATE TABLE `seleniumfiles` (
`fileid`  int(10) NOT NULL AUTO_INCREMENT ,
`testid`  int(10) NULL DEFAULT NULL ,
`seleniumfile`  blob NULL ,
`filename`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`fileid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Table structure for sys_message
-- ----------------------------
DROP TABLE IF EXISTS `sys_message`;
CREATE TABLE `sys_message` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`userid`  int(10) NULL DEFAULT NULL ,
`loginname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`message`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`inserttime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`messagestate`  int(10) NULL DEFAULT 0 ,
`touserid`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1688

;

-- ----------------------------
-- Table structure for sys_url
-- ----------------------------
DROP TABLE IF EXISTS `sys_url`;
CREATE TABLE `sys_url` (
`urlid`  int(10) NOT NULL AUTO_INCREMENT ,
`url`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`urltype`  int(10) NULL DEFAULT NULL ,
`modulename`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`urlid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=25

;

-- ----------------------------
-- Table structure for sys_users
-- ----------------------------
DROP TABLE IF EXISTS `sys_users`;
CREATE TABLE `sys_users` (
`userid`  int(10) NOT NULL AUTO_INCREMENT ,
`loginname`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`username`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`password`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`upanddown`  int(10) NULL DEFAULT 0 ,
`browserpath`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`remark`  varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pa_screen`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pa_screen_win`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`urlid`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`state`  int(10) NULL DEFAULT 1 ,
`level`  int(10) NULL DEFAULT 1 ,
PRIMARY KEY (`userid`, `loginname`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=41

;

-- ----------------------------
-- Table structure for team
-- ----------------------------
DROP TABLE IF EXISTS `team`;
CREATE TABLE `team` (
`teamid`  int(10) NOT NULL AUTO_INCREMENT ,
`teamname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`teamid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=45

;

-- ----------------------------
-- Table structure for testmng
-- ----------------------------
DROP TABLE IF EXISTS `testmng`;
CREATE TABLE `testmng` (
`testid`  int(10) NOT NULL AUTO_INCREMENT ,
`status`  int(10) NULL DEFAULT 1 ,
`projectid`  int(10) NOT NULL ,
`testname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`testno`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`testtext`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`testin`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`testout`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`testresult`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`inserttime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`userid`  int(10) NULL DEFAULT NULL ,
`selenium_share_status`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`testid`, `projectid`, `testname`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1287

;

-- ----------------------------
-- Table structure for user_message
-- ----------------------------
DROP TABLE IF EXISTS `user_message`;
CREATE TABLE `user_message` (
`id`  int(10) NOT NULL AUTO_INCREMENT ,
`loginname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`message`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`userid`  int(10) NULL DEFAULT NULL ,
`inserttime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`touserid`  int(10) NULL DEFAULT NULL ,
`messagestate`  int(10) NULL DEFAULT 0 ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=465

;

-- ----------------------------
-- Table structure for works
-- ----------------------------
DROP TABLE IF EXISTS `works`;
CREATE TABLE `works` (
`workid`  int(10) NOT NULL AUTO_INCREMENT ,
`workname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`worktext`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`userid`  int(10) NULL DEFAULT NULL ,
`inserttime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`starttime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`endtime`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`workid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=25

;

-- ----------------------------
-- Auto increment value for files
-- ----------------------------
ALTER TABLE `files` AUTO_INCREMENT=91;

-- ----------------------------
-- Auto increment value for learn
-- ----------------------------
ALTER TABLE `learn` AUTO_INCREMENT=40;

-- ----------------------------
-- Auto increment value for learn_type
-- ----------------------------
ALTER TABLE `learn_type` AUTO_INCREMENT=12;

-- ----------------------------
-- Auto increment value for project
-- ----------------------------
ALTER TABLE `project` AUTO_INCREMENT=25;

-- ----------------------------
-- Auto increment value for script
-- ----------------------------
ALTER TABLE `script` AUTO_INCREMENT=7652;

-- ----------------------------
-- Auto increment value for seleniumfiles
-- ----------------------------
ALTER TABLE `seleniumfiles` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for sys_message
-- ----------------------------
ALTER TABLE `sys_message` AUTO_INCREMENT=1688;

-- ----------------------------
-- Auto increment value for sys_url
-- ----------------------------
ALTER TABLE `sys_url` AUTO_INCREMENT=25;

-- ----------------------------
-- Auto increment value for sys_users
-- ----------------------------
ALTER TABLE `sys_users` AUTO_INCREMENT=41;

-- ----------------------------
-- Auto increment value for team
-- ----------------------------
ALTER TABLE `team` AUTO_INCREMENT=45;

-- ----------------------------
-- Auto increment value for testmng
-- ----------------------------
ALTER TABLE `testmng` AUTO_INCREMENT=1287;

-- ----------------------------
-- Auto increment value for user_message
-- ----------------------------
ALTER TABLE `user_message` AUTO_INCREMENT=465;

-- ----------------------------
-- Auto increment value for works
-- ----------------------------
ALTER TABLE `works` AUTO_INCREMENT=25;
