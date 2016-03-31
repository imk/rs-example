DROP DATABASE IF EXISTS `oauth`;
CREATE DATABASE  IF NOT EXISTS `oauth` DEFAULT CHARACTER SET utf8;
GRANT ALL PRIVILEGES ON oauth.* TO 'oauth'@'localhost' IDENTIFIED BY 'ohb4Utoop4go';

USE `oauth`;

DROP TABLE IF EXISTS `clients`;
CREATE TABLE `clients` (
  `clientID` bigint(20) NOT NULL AUTO_INCREMENT,
  `applicationDescription` varchar(5000) DEFAULT NULL,
  `applicationName` varchar(255) DEFAULT NULL,
  `callBackURL` varchar(255) DEFAULT NULL,
  `clientKey` varchar(255) NOT NULL,
  `clientSecret` varchar(255) DEFAULT NULL,
  `createTime` datetime DEFAULT NULL,
  PRIMARY KEY (`clientID`),
  UNIQUE KEY `UK_clientKey` (`clientKey`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `webservices`;
CREATE TABLE `webservices` (
  `webserviceID` bigint(20) NOT NULL AUTO_INCREMENT,
  `webserviceName` varchar(255) DEFAULT NULL,
  `webserviceType` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`webserviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `roles`;
CREATE TABLE `roles` (
  `roleID` bigint(20) NOT NULL AUTO_INCREMENT,
  `roleName` varchar(128) DEFAULT NULL,
  `webserviceID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`roleID`),
  KEY `FK_roles_webservices` (`webserviceID`),
  CONSTRAINT `FK_roles_webservices` FOREIGN KEY (`webserviceID`) REFERENCES `webservices` (`webserviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `client_roles`;
CREATE TABLE `client_roles` (
  `clients_clientID` bigint(20) NOT NULL,
  `clientRoles_roleID` bigint(20) NOT NULL,
  KEY `FK_client_roles_roles` (`clientRoles_roleID`),
  KEY `FK_client_roles_clients` (`clients_clientID`),
  CONSTRAINT `FK_client_roles_clients` FOREIGN KEY (`clients_clientID`) REFERENCES `clients` (`clientID`),
  CONSTRAINT `FK_client_roles_roles` FOREIGN KEY (`clientRoles_roleID`) REFERENCES `roles` (`roleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `client_webservice_request_counter`;
CREATE TABLE `client_webservice_request_counter` (
  `requestCounterID` bigint(20) NOT NULL AUTO_INCREMENT,
  `counter` int(11) NOT NULL,
  `startDate` datetime DEFAULT NULL,
  `webserviceID` bigint(20) NOT NULL,
  `clientID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`requestCounterID`),
  KEY `FK_client_webservice_request_counter_webservices` (`webserviceID`),
  KEY `FK_client_webservice_request_counter_clients` (`clientID`),
  CONSTRAINT `FK_client_webservice_request_counter_clients` FOREIGN KEY (`clientID`) REFERENCES `clients` (`clientID`),
  CONSTRAINT `FK_client_webservice_request_counter_webservices` FOREIGN KEY (`webserviceID`) REFERENCES `webservices` (`webserviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `limitations`;
CREATE TABLE `limitations` (
  `limitationID` bigint(20) NOT NULL AUTO_INCREMENT,
  `limitationNumber` bigint(20) NOT NULL,
  `limitationPeriod` bigint(20) NOT NULL,
  `webserviceID` bigint(20) NOT NULL,
  `clientID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`limitationID`),
  KEY `FK_limitations_webservices` (`webserviceID`),
  KEY `FK_limitations_clients` (`clientID`),
  CONSTRAINT `FK_limitations_clients` FOREIGN KEY (`clientID`) REFERENCES `clients` (`clientID`),
  CONSTRAINT `FK_limitations_webservices` FOREIGN KEY (`webserviceID`) REFERENCES `webservices` (`webserviceID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `usersubjects`;
CREATE TABLE `usersubjects` (
  `userSubjectID` bigint(20) NOT NULL AUTO_INCREMENT,
  `login` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`userSubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `tokens`;
CREATE TABLE `tokens` (
  `DTYPE` varchar(31) NOT NULL,
  `tokenKey` varchar(255) NOT NULL,
  `issuedAt` bigint(20) NOT NULL,
  `lifetime` bigint(20) NOT NULL,
  `preAuthorized` bit(1) NOT NULL,
  `tokenSecret` varchar(255) DEFAULT NULL,
  `callback` varchar(255) DEFAULT NULL,
  `tokenState` varchar(255) DEFAULT NULL,
  `verifier` varchar(255) DEFAULT NULL,
  `clientID` bigint(20) NOT NULL,
  `userSubjectID` bigint(20) DEFAULT NULL,
  PRIMARY KEY (`tokenKey`),
  KEY `FK_tokens_clients` (`clientID`),
  KEY `FK_tokens_usersubjects` (`userSubjectID`),
  CONSTRAINT `FK_tokens_clients` FOREIGN KEY (`clientID`) REFERENCES `clients` (`clientID`),
  CONSTRAINT `FK_tokens_usersubjects` FOREIGN KEY (`userSubjectID`) REFERENCES `usersubjects` (`userSubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `usersubjects_roles`;
CREATE TABLE `usersubjects_roles` (
  `usersubjects_userSubjectID` bigint(20) NOT NULL,
  `userRoles_roleID` bigint(20) NOT NULL,
  KEY `FK_usersubjects_roles_roles` (`userRoles_roleID`),
  KEY `FK_usersubjects_roles_usersubjects` (`usersubjects_userSubjectID`),
  CONSTRAINT `FK_usersubjects_roles_roles` FOREIGN KEY (`userRoles_roleID`) REFERENCES `roles` (`roleID`),
  CONSTRAINT `FK_usersubjects_roles_usersubjects` FOREIGN KEY (`usersubjects_userSubjectID`) REFERENCES `usersubjects` (`userSubjectID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `clients` (`clientID`, `applicationName`, `callBackURL`, `clientSecret`, `createTime`, `applicationDescription`, `clientKey`) VALUES
(1, 'RSExampleClient', 'http://localhost:8080/rs-example/', 'Hu6quioToongiet', '2016-03-31 20:15:00', 'Client für Testabfragen am JAX-RS-Besipiel-Service', 'aiv6-eiqu-aiGh-8wie');

INSERT INTO `webservices` (`webserviceID`, `webserviceName`, `webserviceType`) VALUES (1, 'RSExample', 'rest');

INSERT INTO `roles` (`roleID`, `roleName`, `webserviceID`) VALUES (1, 'RSExample_Read', 1);

INSERT INTO `client_roles` (`Clients_clientID`, `clientRoles_roleID`) VALUES (1, 1);
