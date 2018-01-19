/*
Navicat MySQL Data Transfer

Source Server         : local
Source Server Version : 50713
Source Host           : localhost:3306
Source Database       : pathcloud_wechat

Target Server Type    : MYSQL
Target Server Version : 50713
File Encoding         : 65001

Date: 2017-05-24 14:44:48
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `open_id` varchar(255) NOT NULL  UNIQUE,
  `cookie` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
SET FOREIGN_KEY_CHECKS=1;

DROP TABLE IF EXISTS `applicant`;
CREATE TABLE `applicant` (
  `id`              BIGINT(20)   NOT NULL AUTO_INCREMENT,
  `open_id`      VARCHAR(255)  NOT NULL, #微信用户ID
  `first_name`      VARCHAR(30)           DEFAULT NULL, #姓名
  `phone`           VARCHAR(18)           DEFAULT NULL, #联系电话
  `identity`        INT(11)               DEFAULT NULL, #身份
  `tutor`           VARCHAR(50)           DEFAULT NULL, #导师
  `faculty`         VARCHAR(50)           DEFAULT NULL, #院系
  `student_number`  VARCHAR(50)           DEFAULT NULL, #学号
  `unit`            VARCHAR(50)           DEFAULT NULL, #单位
  `wno`             VARCHAR(50)           DEFAULT NULL, #职工号
  `departments`     INT(11)               DEFAULT NULL, #科室
  `task_type`       INT(11)               DEFAULT NULL, #课题类型
  `task_name`       VARCHAR(50)           DEFAULT NULL, #课题名称
  `specialty`       VARCHAR(50)           DEFAULT NULL, #专业
  `project_code`    VARCHAR(50)           DEFAULT NULL, #项目代码
  `create_time`     DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `update_time`     DATETIME              DEFAULT CURRENT_TIMESTAMP,
  `is_self`     BIT (1)             DEFAULT NULL ,
  PRIMARY KEY (`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `username` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `open_id` varchar(255) NOT NULL,
  `cookie` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) DEFAULT NULL,
  `received` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `open_id` (`open_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;