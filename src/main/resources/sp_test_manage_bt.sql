/*
Navicat MySQL Data Transfer

Source Server         : 172.18.7.160_3306
Source Server Version : 50718
Source Host           : 172.18.7.160:3306
Source Database       : sp_test_manage_bt

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-03-10 17:04:21
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
-- Table structure for mc_dict_drug
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_drug`;
CREATE TABLE `mc_dict_drug` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`drugcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`drugname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`drugform`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`druggroupcode`  int(11) NULL DEFAULT NULL ,
`druggroupname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`drggrp_searchcode`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_basedrug`  int(11) NULL DEFAULT '-1' ,
`is_basedrug_p`  int(11) NULL DEFAULT '-1' ,
`is_anti`  int(11) NULL DEFAULT '-1' ,
`antitype`  int(11) NULL DEFAULT '-1' ,
`antilevel`  int(11) NULL DEFAULT '-1' ,
`drugtype`  int(11) NULL DEFAULT '-1' ,
`drugformtype`  int(11) NULL DEFAULT '-1' ,
`socialsecurity_ratio`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_socialsecurity`  int(11) NULL DEFAULT '-1' ,
`socialsecurity_desc`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`jdmtype`  int(11) NULL DEFAULT '-1' ,
`is_stimulant`  int(11) NULL DEFAULT '-1' ,
`stimulantingred`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_solvent`  int(11) NULL DEFAULT '-1' ,
`is_srpreparations`  int(11) NULL DEFAULT '-1' ,
`is_needskintest`  int(11) NULL DEFAULT '-1' ,
`is_dearmed`  int(11) NULL DEFAULT '-1' ,
`is_poison`  int(11) NULL DEFAULT '-1' ,
`is_bloodmed`  int(11) NULL DEFAULT '-1' ,
`is_sugarmed`  int(11) NULL DEFAULT '-1' ,
`otctype`  int(11) NULL DEFAULT '-1' ,
`high_alert_level`  int(11) NULL DEFAULT '-1' ,
`is_bisection_use`  int(11) NULL DEFAULT '-1' ,
`classid`  int(11) NULL DEFAULT '-1' ,
`classtitle`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`operuser`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`opertime`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`typename`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`state`  int(11) NULL DEFAULT 1 ,
`updatedate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `drugcode`),
INDEX `PK_mc_dict_drug_drugcode` (`drugcode`) USING BTREE ,
INDEX `index_match_drugcode` (`match_scheme`, `drugcode`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_drug_pass
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_drug_pass`;
CREATE TABLE `mc_dict_drug_pass` (
`proid`  int(11) NOT NULL AUTO_INCREMENT ,
`drug_unique_code`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`drugcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`drugname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`drugform`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`drugspec`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`comp_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`approvalcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`doseunit`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`match_scheme`  int(11) NOT NULL ,
`pass_drugcode`  int(11) NULL DEFAULT 0 ,
`pass_approvalcode`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_nametype`  int(11) NULL DEFAULT 0 ,
`pass_doseunit`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_drugname`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_form_name`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_st_strength`  varchar(1000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_st_comp_name`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_dividend`  double NULL DEFAULT 0 ,
`pass_divisor`  double NULL DEFAULT 0 ,
`menulabel`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`unable_match`  int(11) NULL DEFAULT 0 ,
`unable_match_desc`  varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`oprpi_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`oprpi_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_upstate`  int(11) NULL DEFAULT 0 ,
`updatedate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`proid`, `drug_unique_code`, `doseunit`, `match_scheme`),
INDEX `PK_mc_dict_drugPass_uniqueCode` (`drug_unique_code`) USING BTREE ,
INDEX `PK_mc_dict_drugPass_doseunit` (`doseunit`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=65625

;

-- ----------------------------
-- Table structure for mc_dict_drug_sub
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_drug_sub`;
CREATE TABLE `mc_dict_drug_sub` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`drugcode`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`drugname`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`drugform`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`drugspec`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`costunit`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`ddd`  decimal(18,3) NULL DEFAULT NULL ,
`dddunit`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`ddd_costunit`  decimal(18,3) NULL DEFAULT NULL ,
`adddate`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_use`  int(11) NOT NULL DEFAULT 1 ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`state`  int(11) NULL DEFAULT 1 ,
`updatedate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `drugcode`, `drugspec`, `costunit`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_hospital_match_relation
-- ----------------------------
DROP TABLE IF EXISTS `mc_hospital_match_relation`;
CREATE TABLE `mc_hospital_match_relation` (
`mhiscode`  bigint(20) NOT NULL AUTO_INCREMENT ,
`doctorgroupmatch_scheme`  int(11) NULL DEFAULT NULL ,
`wardmatch_scheme`  int(11) NULL DEFAULT NULL ,
`hiscode`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`hisname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`drugmatch_scheme`  int(11) NULL DEFAULT NULL ,
`allermatch_scheme`  int(11) NULL DEFAULT NULL ,
`dismatch_scheme`  int(11) NULL DEFAULT NULL ,
`freqmatch_scheme`  int(11) NULL DEFAULT NULL ,
`routematch_scheme`  int(11) NULL DEFAULT NULL ,
`doctormatch_scheme`  int(11) NULL DEFAULT NULL ,
`oprmatch_scheme`  int(11) NULL DEFAULT NULL ,
`costitemmatch_scheme`  int(11) NULL DEFAULT NULL ,
`deptmatch_scheme`  int(11) NULL DEFAULT NULL ,
`exammatch_scheme`  int(11) NULL DEFAULT NULL ,
`labmatch_scheme`  int(11) NULL DEFAULT NULL ,
`labsubmatch_scheme`  int(11) NULL DEFAULT NULL ,
`hiscode_user`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`mhiscode`, `hiscode`),
INDEX `hiscode_user` (`hiscode_user`) USING BTREE ,
INDEX `mhiscode` (`mhiscode`) USING BTREE ,
INDEX `drug_key` (`mhiscode`, `drugmatch_scheme`) USING BTREE ,
INDEX `route_key` (`mhiscode`, `routematch_scheme`) USING BTREE ,
INDEX `dis_key` (`mhiscode`, `dismatch_scheme`) USING BTREE ,
INDEX `doct_key` (`mhiscode`, `doctormatch_scheme`) USING BTREE ,
INDEX `dept_key` (`mhiscode`, `deptmatch_scheme`) USING BTREE ,
INDEX `drug_hiscode_key` (`hiscode`, `drugmatch_scheme`) USING BTREE ,
INDEX `route_hiscode_key` (`hiscode`, `routematch_scheme`) USING BTREE 
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=199008002

;

-- ----------------------------
-- Table structure for prescription
-- ----------------------------
DROP TABLE IF EXISTS `prescription`;
CREATE TABLE `prescription` (
`pre_id`  int(10) NOT NULL AUTO_INCREMENT ,
`patientname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`prescription_json`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`pre_id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=43

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
-- Auto increment value for mc_dict_drug_pass
-- ----------------------------
ALTER TABLE `mc_dict_drug_pass` AUTO_INCREMENT=65625;

-- ----------------------------
-- Auto increment value for mc_hospital_match_relation
-- ----------------------------
ALTER TABLE `mc_hospital_match_relation` AUTO_INCREMENT=199008002;

-- ----------------------------
-- Auto increment value for prescription
-- ----------------------------
ALTER TABLE `prescription` AUTO_INCREMENT=43;

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
