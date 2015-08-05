CREATE DATABASE  IF NOT EXISTS `db_database16` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_database16`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: db_database16
-- ------------------------------------------------------
-- Server version	5.5.24

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
-- Table structure for table `tb_product`
--

DROP TABLE IF EXISTS `tb_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_product` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '主键id',
  `name` varchar(45) NOT NULL COMMENT '商品名称',
  `price` double NOT NULL COMMENT '商品价格',
  `factory` varchar(45) NOT NULL COMMENT '生产商',
  `remark` varchar(200) NOT NULL COMMENT '备注',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product`
--

LOCK TABLES `tb_product` WRITE;
/*!40000 ALTER TABLE `tb_product` DISABLE KEYS */;
INSERT INTO `tb_product` VALUES (1,'Java Web编程词典',79,'明日科技','明日科技出品');
/*!40000 ALTER TABLE `tb_product` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_idcard`
--

DROP TABLE IF EXISTS `tb_idcard`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_idcard` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `IDcard_code` varchar(45) NOT NULL COMMENT '身份证号',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_idcard`
--

LOCK TABLES `tb_idcard` WRITE;
/*!40000 ALTER TABLE `tb_idcard` DISABLE KEYS */;
INSERT INTO `tb_idcard` VALUES (1,'22019523321*****'),(2,'22296325413*****'),(3,'12245435878*****'),(5,'22010411111*****'),(6,'2201031111******');
/*!40000 ALTER TABLE `tb_idcard` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_factory`
--

DROP TABLE IF EXISTS `tb_factory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_factory` (
  `factoryid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `factoryname` varchar(45) NOT NULL COMMENT '生产商名称',
  PRIMARY KEY (`factoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_factory`
--

LOCK TABLES `tb_factory` WRITE;
/*!40000 ALTER TABLE `tb_factory` DISABLE KEYS */;
INSERT INTO `tb_factory` VALUES (1,'吉林省明日科技有限公司'),(2,'明日科技');
/*!40000 ALTER TABLE `tb_factory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_product1`
--

DROP TABLE IF EXISTS `tb_product1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_product1` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL COMMENT '产品名称',
  `price` double NOT NULL COMMENT '产品价格',
  `factoryid` int(10) unsigned NOT NULL COMMENT '关联的产品信息id',
  PRIMARY KEY (`id`),
  KEY `FK_tb_product1` (`factoryid`),
  CONSTRAINT `FK_tb_product1` FOREIGN KEY (`factoryid`) REFERENCES `tb_factory` (`factoryid`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_product1`
--

LOCK TABLES `tb_product1` WRITE;
/*!40000 ALTER TABLE `tb_product1` DISABLE KEYS */;
INSERT INTO `tb_product1` VALUES (1,'Java Web编程宝典',79,2),(2,'Java编程宝典',79,2),(3,'Java Web编程词典珍藏版',798,1),(4,'Java Web编程词典个人版',298,1);
/*!40000 ALTER TABLE `tb_product1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_people`
--

DROP TABLE IF EXISTS `tb_people`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_people` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `age` int(10) unsigned NOT NULL,
  `sex` varchar(2) NOT NULL,
  `card_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_people`
--

LOCK TABLES `tb_people` WRITE;
/*!40000 ALTER TABLE `tb_people` DISABLE KEYS */;
INSERT INTO `tb_people` VALUES (1,'小明',22,'男',1),(2,'小红',20,'女',2),(3,'小刚',30,'男',3),(5,'无语',30,'女',5),(6,'小琦',30,'男',6);
/*!40000 ALTER TABLE `tb_people` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tb_employee`
--

DROP TABLE IF EXISTS `tb_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tb_employee` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `sex` varchar(45) NOT NULL,
  `business` varchar(45) NOT NULL,
  `address` varchar(100) NOT NULL,
  `remark` varchar(200) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tb_employee`
--

LOCK TABLES `tb_employee` WRITE;
/*!40000 ALTER TABLE `tb_employee` DISABLE KEYS */;
INSERT INTO `tb_employee` VALUES (1,'小明','男','项目负责人','长春市二道区***','工作认真，领导能力强'),(2,'小红','女','客服人员1','长春市朝阳区***','工作认真负责'),(3,'小强','男','项目经理','长春市南关区***','领导能力强');
/*!40000 ALTER TABLE `tb_employee` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-07 14:37:18
CREATE DATABASE  IF NOT EXISTS `db_database17` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `db_database17`;
-- MySQL dump 10.13  Distrib 5.5.16, for Win32 (x86)
--
-- Host: localhost    Database: db_database17
-- ------------------------------------------------------
-- Server version	5.5.24

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
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2012-08-07 14:37:18
