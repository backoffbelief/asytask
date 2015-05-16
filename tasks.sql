/*
Navicat MySQL Data Transfer

Source Server         : localhost
Source Server Version : 50130
Source Host           : localhost:3306
Source Database       : asynctask

Target Server Type    : MYSQL
Target Server Version : 50130
File Encoding         : 65001

Date: 2015-03-13 14:52:31
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for `tasks`
-- ----------------------------
DROP TABLE IF EXISTS `tasks`;
CREATE TABLE `tasks` (
`id`  varchar(40) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`type`  varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`subtype`  varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`timeoutmillis`  int(11) NOT NULL ,
`state`  varchar(20) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL ,
`starttime`  datetime NOT NULL ,
`finishtime`  datetime NULL DEFAULT NULL ,
`progress`  int(11) NULL DEFAULT NULL ,
`msg`  varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`flag`  varchar(100) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`inputdata`  varchar(2000) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`outputdata`  varchar(2000) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
`props`  varchar(1000) CHARACTER SET latin1 COLLATE latin1_swedish_ci NULL DEFAULT NULL ,
PRIMARY KEY (`id`)
)
ENGINE=MyISAM
DEFAULT CHARACTER SET=latin1 COLLATE=latin1_swedish_ci

;
