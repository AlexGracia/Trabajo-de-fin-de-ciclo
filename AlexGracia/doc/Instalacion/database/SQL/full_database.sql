-- MySQL dump 10.13  Distrib 5.6.11, for Win32 (x86)
--
-- Host: localhost    Database: alex_gracia
-- ------------------------------------------------------
-- Server version	5.6.11

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
-- Current Database: `alex_gracia`
--

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `alex_gracia` /*!40100 DEFAULT CHARACTER SET latin1 */;

USE `alex_gracia`;

--
-- Table structure for table `clientes`
--

DROP TABLE IF EXISTS `clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `clientes` (
  `ID_CLIENTES` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CODIGO_CLIENTES` varchar(100) DEFAULT 'sin codigo',
  `NOMBRE` varchar(50) NOT NULL,
  `APELLIDOS` varchar(100) DEFAULT 'desconocidos',
  `DNI` varchar(9) NOT NULL,
  `TELEFONO` int(11) DEFAULT '0',
  `FECHA_NACIMIENTO` date DEFAULT NULL,
  `FECHA_CARNET` date DEFAULT NULL,
  `DIRECCION` varchar(200) DEFAULT 'desconocida',
  PRIMARY KEY (`ID_CLIENTES`),
  UNIQUE KEY `DNI` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `clientes`
--

LOCK TABLES `clientes` WRITE;
/*!40000 ALTER TABLE `clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extras`
--

DROP TABLE IF EXISTS `extras`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `extras` (
  `ID_EXTRAS` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `ANO_FABRICACION` date DEFAULT NULL,
  `MARCA` varchar(100) DEFAULT 'desconocida',
  `NOMBRE` varchar(100) NOT NULL,
  `MODELO` varchar(100) NOT NULL,
  `DESCRIPCION` varchar(250) DEFAULT 'sin descripcion',
  `LUGAR_ORIGEN` varchar(100) DEFAULT 'desconocido',
  `DIMENSIONES` varchar(100) NOT NULL,
  `FABRICANTE` varchar(100) DEFAULT 'desconocido',
  PRIMARY KEY (`ID_EXTRAS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extras`
--

LOCK TABLES `extras` WRITE;
/*!40000 ALTER TABLE `extras` DISABLE KEYS */;
/*!40000 ALTER TABLE `extras` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `extras_vehiculos`
--

DROP TABLE IF EXISTS `extras_vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `extras_vehiculos` (
  `ID_EXTRA` int(10) unsigned NOT NULL,
  `ID_VEHICULO` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID_EXTRA`,`ID_VEHICULO`),
  KEY `ID_EXTRA` (`ID_EXTRA`),
  KEY `ID_VEHICULO` (`ID_VEHICULO`),
  CONSTRAINT `extras_vehiculos_ibfk_1` FOREIGN KEY (`ID_EXTRA`) REFERENCES `extras` (`ID_EXTRAS`),
  CONSTRAINT `extras_vehiculos_ibfk_2` FOREIGN KEY (`ID_VEHICULO`) REFERENCES `vehiculos` (`ID_VEHICULOS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `extras_vehiculos`
--

LOCK TABLES `extras_vehiculos` WRITE;
/*!40000 ALTER TABLE `extras_vehiculos` DISABLE KEYS */;
/*!40000 ALTER TABLE `extras_vehiculos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piezas`
--

DROP TABLE IF EXISTS `piezas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `piezas` (
  `ID_PIEZAS` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `CODIGO_PIEZAS` int(11) DEFAULT '0',
  `NOMBRE` varchar(100) NOT NULL,
  `DESCRIPCION` varchar(250) DEFAULT NULL,
  `CANTIDAD` int(11) DEFAULT '0',
  `PRECIO` int(11) DEFAULT '0',
  `LUGAR_ORIGEN` varchar(200) NOT NULL,
  `FECHA_SOLICITUD` date DEFAULT NULL,
  `MARCA` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`ID_PIEZAS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piezas`
--

LOCK TABLES `piezas` WRITE;
/*!40000 ALTER TABLE `piezas` DISABLE KEYS */;
/*!40000 ALTER TABLE `piezas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piezas_proveedores`
--

DROP TABLE IF EXISTS `piezas_proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `piezas_proveedores` (
  `ID_PIEZA` int(10) unsigned NOT NULL,
  `ID_PROVEEDOR` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID_PIEZA`,`ID_PROVEEDOR`),
  KEY `ID_PIEZA` (`ID_PIEZA`),
  KEY `ID_PROVEEDOR` (`ID_PROVEEDOR`),
  CONSTRAINT `piezas_proveedores_ibfk_1` FOREIGN KEY (`ID_PIEZA`) REFERENCES `piezas` (`ID_PIEZAS`),
  CONSTRAINT `piezas_proveedores_ibfk_2` FOREIGN KEY (`ID_PROVEEDOR`) REFERENCES `proveedores` (`ID_PROVEEDORES`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piezas_proveedores`
--

LOCK TABLES `piezas_proveedores` WRITE;
/*!40000 ALTER TABLE `piezas_proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `piezas_proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `piezas_talleres`
--

DROP TABLE IF EXISTS `piezas_talleres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `piezas_talleres` (
  `ID_PIEZA` int(10) unsigned NOT NULL,
  `ID_TALLER` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID_PIEZA`,`ID_TALLER`),
  KEY `ID_PIEZA` (`ID_PIEZA`),
  KEY `ID_TALLER` (`ID_TALLER`),
  CONSTRAINT `piezas_talleres_ibfk_1` FOREIGN KEY (`ID_PIEZA`) REFERENCES `piezas` (`ID_PIEZAS`),
  CONSTRAINT `piezas_talleres_ibfk_2` FOREIGN KEY (`ID_TALLER`) REFERENCES `talleres` (`ID_TALLERES`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `piezas_talleres`
--

LOCK TABLES `piezas_talleres` WRITE;
/*!40000 ALTER TABLE `piezas_talleres` DISABLE KEYS */;
/*!40000 ALTER TABLE `piezas_talleres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `polizas`
--

DROP TABLE IF EXISTS `polizas`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `polizas` (
  `ID_POLIZAS` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NUMERO` int(11) DEFAULT '0',
  `TIPO` enum('','A_todo_riesgo','A_terceros') DEFAULT NULL,
  `IMPORTE` int(11) DEFAULT '0',
  `ESTADO` enum('','Alta','Suspension','Baja') DEFAULT NULL,
  `FECHA_INICIO` date DEFAULT NULL,
  `CANTIDAD_CONDUCTORES` int(11) DEFAULT '0',
  `ANTIGUEDAD_CONDUCCION` date DEFAULT NULL,
  `FECHA_FIN` date NOT NULL,
  `ID_CLIENTE` int(10) unsigned NOT NULL,
  `ID_VEHICULO` int(10) unsigned NOT NULL,
  PRIMARY KEY (`ID_POLIZAS`),
  KEY `ID_CLIENTE` (`ID_CLIENTE`),
  KEY `ID_VEHICULO` (`ID_VEHICULO`),
  CONSTRAINT `polizas_ibfk_1` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `clientes` (`ID_CLIENTES`),
  CONSTRAINT `polizas_ibfk_2` FOREIGN KEY (`ID_VEHICULO`) REFERENCES `vehiculos` (`ID_VEHICULOS`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `polizas`
--

LOCK TABLES `polizas` WRITE;
/*!40000 ALTER TABLE `polizas` DISABLE KEYS */;
/*!40000 ALTER TABLE `polizas` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `proveedores`
--

DROP TABLE IF EXISTS `proveedores`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `proveedores` (
  `ID_PROVEEDORES` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(100) NOT NULL,
  `TELEFONO` int(11) DEFAULT '0',
  `CORREO_ELECTRONICO` varchar(100) DEFAULT NULL,
  `FECHA_NACIMIENTO` date DEFAULT NULL,
  `DIRECCION` varchar(200) DEFAULT NULL,
  `FACILIDAD_PAGO` enum('','Con_plazos','Sin_plazos') DEFAULT NULL,
  `NOMBRE_EMPRESA` varchar(100) NOT NULL,
  `DNI` varchar(9) NOT NULL,
  PRIMARY KEY (`ID_PROVEEDORES`),
  UNIQUE KEY `DNI` (`DNI`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `proveedores`
--

LOCK TABLES `proveedores` WRITE;
/*!40000 ALTER TABLE `proveedores` DISABLE KEYS */;
/*!40000 ALTER TABLE `proveedores` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `siniestros`
--

DROP TABLE IF EXISTS `siniestros`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `siniestros` (
  `ID_SINIESTROS` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `DATOS_POLIZA` varchar(200) DEFAULT NULL,
  `IMPORTE_REPARACION` double DEFAULT '0',
  `DATOS_CLIENTE` varchar(200) DEFAULT NULL,
  `FECHA_REPARACION` date DEFAULT NULL,
  `DATOS_TALLER` varchar(200) DEFAULT NULL,
  `FECHA_SINIESTRO` datetime DEFAULT NULL,
  `CANTIDAD_VEHICULOS_IMPLICADOS` int(11) DEFAULT '1',
  `ID_CLIENTE` int(10) unsigned NOT NULL,
  `ID_TALLER` int(10) unsigned NOT NULL,
  `CLIENTES_HERIDOS` int(11) DEFAULT '0',
  PRIMARY KEY (`ID_SINIESTROS`),
  KEY `ID_CLIENTE` (`ID_CLIENTE`),
  KEY `ID_TALLER` (`ID_TALLER`),
  CONSTRAINT `siniestros_ibfk_1` FOREIGN KEY (`ID_CLIENTE`) REFERENCES `clientes` (`ID_CLIENTES`),
  CONSTRAINT `siniestros_ibfk_2` FOREIGN KEY (`ID_TALLER`) REFERENCES `talleres` (`ID_TALLERES`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `siniestros`
--

LOCK TABLES `siniestros` WRITE;
/*!40000 ALTER TABLE `siniestros` DISABLE KEYS */;
/*!40000 ALTER TABLE `siniestros` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `talleres`
--

DROP TABLE IF EXISTS `talleres`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `talleres` (
  `ID_TALLERES` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NOMBRE` varchar(100) NOT NULL,
  `DIRECCION` varchar(200) NOT NULL,
  `TELEFONO` int(11) DEFAULT '0',
  `FECHA_INICIO` date DEFAULT NULL,
  `NOMBRE_JEFE` varchar(100) DEFAULT NULL,
  `CIF_EMPRESA` varchar(20) NOT NULL,
  `NUMERO_TRABAJADORES` int(11) DEFAULT '0',
  `CANTIDAD_REPARACIONES` int(11) DEFAULT '0',
  PRIMARY KEY (`ID_TALLERES`),
  UNIQUE KEY `CIF_EMPRESA` (`CIF_EMPRESA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `talleres`
--

LOCK TABLES `talleres` WRITE;
/*!40000 ALTER TABLE `talleres` DISABLE KEYS */;
/*!40000 ALTER TABLE `talleres` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `login` varchar(20) NOT NULL,
  `password` varchar(50) NOT NULL,
  `rango` enum('user','admin','tecnic') DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `login` (`login`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'user','','user'),(2,'admin','admin','admin'),(3,'tecnic','tecnic','tecnic');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vehiculos`
--

DROP TABLE IF EXISTS `vehiculos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `vehiculos` (
  `ID_VEHICULOS` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `NUMERO_CHASIS` varchar(17) DEFAULT 'sin chasis',
  `MATRICULA` varchar(100) NOT NULL,
  `MARCA` varchar(100) DEFAULT 'desconocida',
  `MODELO` varchar(100) DEFAULT 'desconocido',
  `POTENCIA` int(11) DEFAULT '0',
  `ANO_FABRICACION` date DEFAULT NULL,
  `COLOR` enum('','Metalizado','Mate') DEFAULT NULL,
  `NUMERO_PUERTAS` int(1) DEFAULT '0',
  `KILOMETROS` int(11) DEFAULT '0',
  PRIMARY KEY (`ID_VEHICULOS`),
  UNIQUE KEY `MATRICULA` (`MATRICULA`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vehiculos`
--

LOCK TABLES `vehiculos` WRITE;
/*!40000 ALTER TABLE `vehiculos` DISABLE KEYS */;
/*!40000 ALTER TABLE `vehiculos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2015-03-24 16:03:17
