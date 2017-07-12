-- MySQL dump 10.13  Distrib 5.7.15, for Win64 (x86_64)
--
-- Host: localhost    Database: seckill
-- ------------------------------------------------------
-- Server version	5.7.15-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `seckill`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `seckill` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `seckill`;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `product_name` varchar(100) NOT NULL,
  `product_num` int(11) NOT NULL,
  `product_price` double NOT NULL COMMENT 'in canadian dollars',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'seckill start time',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'seckill end time',
  PRIMARY KEY (`id`),
  KEY `start_time_idx` (`start_time`),
  KEY `end_time_idx` (`end_time`),
  KEY `create_time_idx` (`create_time`)
) ENGINE=InnoDB AUTO_INCREMENT=1004 DEFAULT CHARSET=utf8 COMMENT='The table for seckill products';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1000,'iphone6',98,120,'2017-01-16 21:59:22','2016-01-01 05:00:00','2017-01-30 18:46:13'),(1001,'ps4',47,100,'2017-01-16 21:59:22','2017-02-12 19:00:00','2017-05-01 04:00:00'),(1002,'xbox one',46,80,'2017-01-16 21:59:22','2016-01-04 05:00:00','2017-05-01 04:00:00'),(1003,'mac pro',90,70,'2017-01-16 21:59:22','2016-01-04 05:00:00','2017-05-01 04:00:00');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products_copy`
--

DROP TABLE IF EXISTS `products_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products_copy` (
  `id` bigint(20) NOT NULL DEFAULT '0',
  `product_name` varchar(100) NOT NULL,
  `product_num` int(11) NOT NULL,
  `product_price` double NOT NULL COMMENT 'in canadian dollars',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `start_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'seckill start time',
  `end_time` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00' COMMENT 'seckill end time'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products_copy`
--

LOCK TABLES `products_copy` WRITE;
/*!40000 ALTER TABLE `products_copy` DISABLE KEYS */;
INSERT INTO `products_copy` VALUES (1000,'iphone6',100,120,'2017-01-16 21:59:22','2016-01-01 05:00:00','2016-01-02 05:00:00'),(1001,'ps4',50,100,'2017-01-16 21:59:22','2016-01-02 05:00:00','2016-01-03 05:00:00'),(1002,'xbox one',50,80,'2017-01-16 21:59:22','2016-01-04 05:00:00','2016-01-05 05:00:00'),(1003,'mac pro',100,70,'2017-01-16 21:59:22','2016-01-04 05:00:00','2016-01-05 05:00:00');
/*!40000 ALTER TABLE `products_copy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `success_killed`
--

DROP TABLE IF EXISTS `success_killed`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `success_killed` (
  `product_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '-1:invalid 0:success 1:paid 2:shipped',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`product_id`,`user_phone`) COMMENT 'prevent the same user from purchasing the same product more than once',
  KEY `create_time_idx` (`create_time`),
  CONSTRAINT `success_killed_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `success_killed`
--

LOCK TABLES `success_killed` WRITE;
/*!40000 ALTER TABLE `success_killed` DISABLE KEYS */;
INSERT INTO `success_killed` VALUES (1000,1358779099,0,'2017-01-30 18:28:27'),(1000,6455506511,0,'2017-01-30 18:32:20'),(1000,6552205644,0,'2017-01-18 17:54:11'),(1001,6515506511,0,'2017-02-04 20:26:54'),(1001,6789065457,0,'2017-02-11 06:09:04'),(1001,12345567888,0,'2017-02-04 02:31:08'),(1002,1234567,0,'2017-02-11 19:33:50'),(1002,6515506511,0,'2017-02-10 23:27:44'),(1002,6789065457,0,'2017-02-11 05:57:43'),(1002,37573259389589439,0,'2017-02-11 20:03:06'),(1003,67890,0,'2017-02-11 05:16:27'),(1003,6220017,0,'2017-02-11 05:14:52'),(1003,21346745,0,'2017-02-11 05:17:33'),(1003,63344556,0,'2017-02-11 05:15:31'),(1003,1234556789,0,'2017-02-04 02:22:17'),(1003,6495506511,0,'2017-02-11 05:09:27'),(1003,6789065457,0,'2017-02-11 05:38:40'),(1003,12345567888,0,'2017-02-04 02:25:09'),(1003,234512345677,0,'2017-02-11 05:37:16'),(1003,37573259389589439,0,'2017-02-11 20:00:50');
/*!40000 ALTER TABLE `success_killed` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `success_killed_copy`
--

DROP TABLE IF EXISTS `success_killed_copy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `success_killed_copy` (
  `product_id` bigint(20) NOT NULL,
  `user_phone` bigint(20) NOT NULL,
  `state` tinyint(4) NOT NULL DEFAULT '-1' COMMENT '-1:invalid 0:success 1:paid 2:shipped',
  `create_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `success_killed_copy`
--

LOCK TABLES `success_killed_copy` WRITE;
/*!40000 ALTER TABLE `success_killed_copy` DISABLE KEYS */;
INSERT INTO `success_killed_copy` VALUES (1000,6552205644,0,'2017-01-18 17:54:11');
/*!40000 ALTER TABLE `success_killed_copy` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-03-27 23:44:21
