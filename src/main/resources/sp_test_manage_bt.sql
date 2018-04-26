/*
Navicat MySQL Data Transfer

Source Server         : 172.18.7.160_3306
Source Server Version : 50718
Source Host           : 172.18.7.160:3306
Source Database       : sp_test_manage_bt

Target Server Type    : MYSQL
Target Server Version : 50718
File Encoding         : 65001

Date: 2018-04-26 11:20:26
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
-- Table structure for mc_dict_allergen
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_allergen`;
CREATE TABLE `mc_dict_allergen` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`allercode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`allername`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`pass_allerid`  int(11) NULL DEFAULT 0 ,
`pass_allertype`  int(11) NULL DEFAULT 0 ,
`pass_allername`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`unable_match`  int(11) NULL DEFAULT 0 ,
`unable_match_desc`  varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`createdate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `allercode`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_costitem
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_costitem`;
CREATE TABLE `mc_dict_costitem` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`itemcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`itemname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`itemtype`  int(11) NULL DEFAULT '-1' ,
`is_byx`  int(11) NULL DEFAULT '-1' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`inserttime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `itemcode`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_dept
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_dept`;
CREATE TABLE `mc_dict_dept` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`deptcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`deptname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_clinic`  int(11) NULL DEFAULT '-1' ,
`is_inhosp`  int(11) NULL DEFAULT '-1' ,
`is_emergency`  int(11) NULL DEFAULT '-1' ,
`is_save`  int(11) NULL DEFAULT 0 ,
`inserttime`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`deptcode`, `match_scheme`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_disease
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_disease`;
CREATE TABLE `mc_dict_disease` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`discode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`disname`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`typecode`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`typename`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`dis_type`  int(11) NULL DEFAULT '-1' ,
`is_mxb`  int(11) NOT NULL DEFAULT '-1' ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`pass_icd_code`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`pass_icd_name`  varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`unable_match`  int(11) NULL DEFAULT 0 ,
`unable_match_desc`  varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`createdate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `discode`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_doctor
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_doctor`;
CREATE TABLE `mc_dict_doctor` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`doctorcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' ,
`doctorname`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`deptcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`deptname`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`doctorlevel`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`ilevel`  int(11) NULL DEFAULT '-1' ,
`is_clinic`  int(11) NULL DEFAULT '-1' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`prespriv`  int(11) NULL DEFAULT 1 ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`password`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '63+lFq5e5+aJseZ/cuRKjA==' ,
`antilevel`  int(11) NULL DEFAULT '-1' ,
`createdate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `doctorcode`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

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
-- Table structure for mc_dict_exam
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_exam`;
CREATE TABLE `mc_dict_exam` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`examcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`examname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`type`  int(11) NULL DEFAULT '-1' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`updatedate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`examcode`, `match_scheme`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_frequency
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_frequency`;
CREATE TABLE `mc_dict_frequency` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`frequency`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`times`  int(11) NULL DEFAULT '-1' ,
`days`  int(11) NULL DEFAULT '-1' ,
`pharmfrequency`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`unable_match`  int(11) NULL DEFAULT 0 ,
`match_desc`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`createdate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `frequency`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_lab
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_lab`;
CREATE TABLE `mc_dict_lab` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`labcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`labname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`type`  int(11) NULL DEFAULT '-1' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_save`  int(11) NULL DEFAULT 0 ,
`updatedate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`labname`, `labcode`, `match_scheme`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_labsub
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_labsub`;
CREATE TABLE `mc_dict_labsub` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`itemcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`itemname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`type_id`  int(11) NULL DEFAULT '-1' ,
`medshow_id`  int(11) NULL DEFAULT NULL ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`updatedate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`itemcode`, `itemname`, `match_scheme`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_operation
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_operation`;
CREATE TABLE `mc_dict_operation` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`operationcode`  varchar(64) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`operationname`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`typename`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`useanti`  int(11) NULL DEFAULT '-1' ,
`premoment_low`  double NULL DEFAULT '-1' ,
`premoment_high`  double NULL DEFAULT '-1' ,
`drugtime`  int(11) NULL DEFAULT '-1' ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`createdate`  timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `operationcode`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for mc_dict_route
-- ----------------------------
DROP TABLE IF EXISTS `mc_dict_route`;
CREATE TABLE `mc_dict_route` (
`match_scheme`  int(11) NOT NULL DEFAULT 0 ,
`routecode`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`routename`  varchar(128) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`searchcode`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`route_type`  int(11) NULL DEFAULT '-1' ,
`abbrev`  varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`is_save`  int(11) NOT NULL DEFAULT 0 ,
`pass_routeid`  int(11) NULL DEFAULT 0 ,
`pass_route_name`  varchar(256) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_user`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`match_time`  varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`unable_match`  int(11) NULL DEFAULT 0 ,
`unable_match_desc`  varchar(70) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`isskintest`  int(11) NULL DEFAULT 0 ,
`createdate`  timestamp NULL DEFAULT CURRENT_TIMESTAMP ,
PRIMARY KEY (`match_scheme`, `routecode`)
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
-- Table structure for mc_modulename
-- ----------------------------
DROP TABLE IF EXISTS `mc_modulename`;
CREATE TABLE `mc_modulename` (
`pkid`  int(11) NOT NULL ,
`moduletype`  int(11) NULL DEFAULT NULL ,
`moduleid`  int(11) NULL DEFAULT NULL ,
`modulename`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`pharm_modulename`  varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`seqnum`  int(11) NULL DEFAULT NULL ,
`has_std`  int(11) NULL DEFAULT NULL ,
`has_custom`  int(11) NULL DEFAULT NULL ,
`has_shield`  int(11) NULL DEFAULT NULL ,
`is_show_menu`  int(11) NULL DEFAULT NULL ,
PRIMARY KEY (`pkid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci

;

-- ----------------------------
-- Table structure for pa_files
-- ----------------------------
DROP TABLE IF EXISTS `pa_files`;
CREATE TABLE `pa_files` (
`fileid`  int(10) NOT NULL AUTO_INCREMENT ,
`linkid`  int(10) NULL DEFAULT NULL ,
`linkfile`  longblob NULL ,
`linktype`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`fileid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=90

;

-- ----------------------------
-- Table structure for pa_project
-- ----------------------------
DROP TABLE IF EXISTS `pa_project`;
CREATE TABLE `pa_project` (
`projectid`  int(10) NOT NULL AUTO_INCREMENT ,
`projectname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`teamid`  int(10) NOT NULL ,
PRIMARY KEY (`projectid`, `projectname`, `teamid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=21

;

-- ----------------------------
-- Table structure for pa_script
-- ----------------------------
DROP TABLE IF EXISTS `pa_script`;
CREATE TABLE `pa_script` (
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
-- Table structure for pa_team
-- ----------------------------
DROP TABLE IF EXISTS `pa_team`;
CREATE TABLE `pa_team` (
`teamid`  int(10) NOT NULL AUTO_INCREMENT ,
`teamname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`teamid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=42

;

-- ----------------------------
-- Table structure for pa_testmng
-- ----------------------------
DROP TABLE IF EXISTS `pa_testmng`;
CREATE TABLE `pa_testmng` (
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
`testout_response`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`testid`, `projectid`, `testname`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=714

;

-- ----------------------------
-- Table structure for pass_anli_err
-- ----------------------------
DROP TABLE IF EXISTS `pass_anli_err`;
CREATE TABLE `pass_anli_err` (
`errid`  int(10) NOT NULL AUTO_INCREMENT ,
`testid`  int(10) NOT NULL ,
`testname`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`json_err`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`errid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=79

;

-- ----------------------------
-- Table structure for pass_files
-- ----------------------------
DROP TABLE IF EXISTS `pass_files`;
CREATE TABLE `pass_files` (
`fileid`  int(10) NOT NULL AUTO_INCREMENT ,
`linkid`  int(10) NULL DEFAULT NULL ,
`linkfile`  longblob NULL ,
`linktype`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`fileid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=1

;

-- ----------------------------
-- Table structure for pass_project
-- ----------------------------
DROP TABLE IF EXISTS `pass_project`;
CREATE TABLE `pass_project` (
`projectid`  int(10) NOT NULL AUTO_INCREMENT ,
`projectname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`teamid`  int(10) NOT NULL ,
PRIMARY KEY (`projectid`, `projectname`, `teamid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=29

;

-- ----------------------------
-- Table structure for pass_script
-- ----------------------------
DROP TABLE IF EXISTS `pass_script`;
CREATE TABLE `pass_script` (
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
AUTO_INCREMENT=1

;

-- ----------------------------
-- Table structure for pass_team
-- ----------------------------
DROP TABLE IF EXISTS `pass_team`;
CREATE TABLE `pass_team` (
`teamid`  int(10) NOT NULL AUTO_INCREMENT ,
`teamname`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`remark`  varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
PRIMARY KEY (`teamid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=52

;

-- ----------------------------
-- Table structure for pass_testmng
-- ----------------------------
DROP TABLE IF EXISTS `pass_testmng`;
CREATE TABLE `pass_testmng` (
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
`moduleid`  int(11) NULL DEFAULT NULL ,
`modulename`  varchar(255) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`testout_response`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`anlitype`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`testid`, `projectid`, `testname`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=81974

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
AUTO_INCREMENT=44

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
-- Table structure for sa_gather_log
-- ----------------------------
DROP TABLE IF EXISTS `sa_gather_log`;
CREATE TABLE `sa_gather_log` (
`id`  int(11) NOT NULL AUTO_INCREMENT ,
`anlitype`  int(10) NULL DEFAULT 0 ,
`moduleid`  int(10) NULL DEFAULT NULL ,
`modulename`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`testname`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT '' ,
`gatherbaseinfo`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`gatherresult`  mediumtext CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
PRIMARY KEY (`id`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=20

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
-- Table structure for serverip
-- ----------------------------
DROP TABLE IF EXISTS `serverip`;
CREATE TABLE `serverip` (
`serverid`  int(10) NOT NULL AUTO_INCREMENT ,
`servername`  varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL ,
`serveraddress`  text CHARACTER SET utf8 COLLATE utf8_general_ci NULL ,
`iptype`  int(10) NULL DEFAULT NULL ,
PRIMARY KEY (`serverid`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=27

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
`remark`  varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci NULL DEFAULT NULL ,
`state`  int(10) NULL DEFAULT 1 ,
`level`  int(10) NULL DEFAULT 1 ,
PRIMARY KEY (`userid`, `loginname`)
)
ENGINE=InnoDB
DEFAULT CHARACTER SET=utf8 COLLATE=utf8_general_ci
AUTO_INCREMENT=46

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
AUTO_INCREMENT=46

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
-- Auto increment value for pa_files
-- ----------------------------
ALTER TABLE `pa_files` AUTO_INCREMENT=90;

-- ----------------------------
-- Auto increment value for pa_project
-- ----------------------------
ALTER TABLE `pa_project` AUTO_INCREMENT=21;

-- ----------------------------
-- Auto increment value for pa_script
-- ----------------------------
ALTER TABLE `pa_script` AUTO_INCREMENT=7652;

-- ----------------------------
-- Auto increment value for pa_team
-- ----------------------------
ALTER TABLE `pa_team` AUTO_INCREMENT=42;

-- ----------------------------
-- Auto increment value for pa_testmng
-- ----------------------------
ALTER TABLE `pa_testmng` AUTO_INCREMENT=714;

-- ----------------------------
-- Auto increment value for pass_anli_err
-- ----------------------------
ALTER TABLE `pass_anli_err` AUTO_INCREMENT=79;

-- ----------------------------
-- Auto increment value for pass_files
-- ----------------------------
ALTER TABLE `pass_files` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for pass_project
-- ----------------------------
ALTER TABLE `pass_project` AUTO_INCREMENT=29;

-- ----------------------------
-- Auto increment value for pass_script
-- ----------------------------
ALTER TABLE `pass_script` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for pass_team
-- ----------------------------
ALTER TABLE `pass_team` AUTO_INCREMENT=52;

-- ----------------------------
-- Auto increment value for pass_testmng
-- ----------------------------
ALTER TABLE `pass_testmng` AUTO_INCREMENT=81974;

-- ----------------------------
-- Auto increment value for prescription
-- ----------------------------
ALTER TABLE `prescription` AUTO_INCREMENT=44;

-- ----------------------------
-- Auto increment value for project
-- ----------------------------
ALTER TABLE `project` AUTO_INCREMENT=25;

-- ----------------------------
-- Auto increment value for sa_gather_log
-- ----------------------------
ALTER TABLE `sa_gather_log` AUTO_INCREMENT=20;

-- ----------------------------
-- Auto increment value for script
-- ----------------------------
ALTER TABLE `script` AUTO_INCREMENT=7652;

-- ----------------------------
-- Auto increment value for seleniumfiles
-- ----------------------------
ALTER TABLE `seleniumfiles` AUTO_INCREMENT=1;

-- ----------------------------
-- Auto increment value for serverip
-- ----------------------------
ALTER TABLE `serverip` AUTO_INCREMENT=27;

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
ALTER TABLE `sys_users` AUTO_INCREMENT=46;

-- ----------------------------
-- Auto increment value for team
-- ----------------------------
ALTER TABLE `team` AUTO_INCREMENT=46;

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
