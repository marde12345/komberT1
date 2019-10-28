/*
SQLyog Professional v12.5.1 (64 bit)
MySQL - 10.1.37-MariaDB : Database - indoor_positioning
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`indoor_positioning` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `indoor_positioning`;

/*Table structure for table `access_points` */

DROP TABLE IF EXISTS `access_points`;

CREATE TABLE `access_points` (
  `building_id` char(50) NOT NULL,
  `ssid` char(50) NOT NULL,
  `mac_id` char(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `access_points` */

insert  into `access_points`(`building_id`,`ssid`,`mac_id`) values 
('mis','MIS FANATIK','8c:0c:a3:28:9c:b7'),
('mis','Informatics_Wifi','04:18:d6:0b:8e:90'),
('lab','MIS FANATIK','8c:0c:a3:28:9c:b7'),
('lab','Informatics_Wifi','04:18:d6:0b:8e:90'),
('lab','Mission Possible','00:26:5a:b3:a7:a0');

/*Table structure for table `readings` */

DROP TABLE IF EXISTS `readings`;

CREATE TABLE `readings` (
  `building_id` char(50) NOT NULL,
  `position_id` char(50) NOT NULL,
  `mac_id` char(50) NOT NULL,
  `ssid` char(50) NOT NULL,
  `rssi` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

/*Data for the table `readings` */

insert  into `readings`(`building_id`,`position_id`,`mac_id`,`ssid`,`rssi`) values 
('lab','andre','f0:9f:c2:8f:05:f0','worklink',-81),
('lab','andre','8c:0c:a3:28:9c:b7','MIS FANATIK',-31),
('lab','andre','00:26:5a:b3:a7:a0','Mission Possible',-69),
('lab','andre','f0:9f:c2:74:5a:88','ITS-WIFI-RC',-88),
('lab','andre','f0:9f:c2:74:5b:67','ITS-WIFI-RC',-89),
('lab','andre','b0:48:7a:f9:59:a2','Atas Analisa Room',-87),
('lab','andre','4c:5e:0c:9d:fb:ff','Wifi-AJK',-91),
('lab','andre','b4:75:0e:16:73:b6','test',-92),
('lab','andre','04:18:d6:0b:8e:90','Informatics_Wifi',-68),
('lab','andre','f0:9f:c2:74:5e:f6','ITS-WIFI-RC',-93),
('lab','andre','f0:9f:c2:81:3c:34','workspace',-79),
('lab','aqua','48:fd:8e:ba:07:80','ITS-WIFI',-92),
('lab','aqua','f0:9f:c2:74:5a:88','ITS-WIFI-RC',-86),
('lab','aqua','48:fd:8e:ba:07:81','ITS-WIFI-RC',-90),
('lab','aqua','4c:5e:0c:9d:fb:ff','Wifi-AJK',-90),
('lab','aqua','f0:9f:c2:8f:05:f0','worklink',-86),
('lab','aqua','8c:0c:a3:28:9c:b7','MIS FANATIK',-53),
('lab','aqua','00:26:5a:b3:a7:a0','Mission Possible',-63),
('lab','aqua','b0:48:7a:f9:59:a2','Atas Analisa Room',-89),
('lab','aqua','04:18:d6:0b:8e:90','Informatics_Wifi',-69),
('lab','aqua','f0:9f:c2:74:5b:7b','ITS-WIFI-RC',-88),
('lab','aqua','f0:9f:c2:74:5e:f6','ITS-WIFI-RC',-92),
('lab','aqua','f0:9f:c2:81:3c:34','workspace',-84),
('lab','aqua','fc:ec:da:47:0d:7f','Informatics_Wifi',-89),
('lab','fasma','40:31:3c:98:aa:c0','ROOF-RC',-85),
('lab','fasma','f0:9f:c2:8f:05:f0','worklink',-81),
('lab','fasma','8c:0c:a3:28:9c:b7','MIS FANATIK',-55),
('lab','fasma','00:26:5a:b3:a7:a0','Mission Possible',-73),
('lab','fasma','b0:48:7a:f9:59:a2','Atas Analisa Room',-83),
('lab','fasma','f0:9f:c2:74:5a:88','ITS-WIFI-RC',-84),
('lab','fasma','f0:9f:c2:74:5b:67','ITS-WIFI-RC',-83),
('lab','fasma','04:18:d6:0b:8e:90','Informatics_Wifi',-68),
('lab','fasma','f0:9f:c2:74:5b:7b','ITS-WIFI-RC',-83),
('lab','fasma','f0:9f:c2:81:3c:34','workspace',-77);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
