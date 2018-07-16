DROP TABLE IF EXISTS  `BD_AIRPORT`.`luggage`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`ticket`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`caddieitem`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`caddie`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`passenger`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`flight`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`geographiczone`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`airplane`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`airline`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`agent`;
DROP TABLE IF EXISTS  `BD_AIRPORT`.`job`;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`job` (
	`idjob` int(11) NOT NULL,
	`name` varchar(100) NOT NULL,
	PRIMARY KEY (`idjob`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`agent` (
	`login` varchar(50) NOT NULL,
	`password` varchar(50) NOT NULL,
	`lastname` varchar(50) NOT NULL,
	`firstname` varchar(50) DEFAULT NULL,
	`fk_idjob` int(11) NOT NULL,
	PRIMARY KEY (`login`),
	KEY `fk_job` (`fk_idjob`),
	CONSTRAINT `fk_job` FOREIGN KEY (`fk_idjob`) REFERENCES `BD_AIRPORT`.`job` (`idjob`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`airline` (
	`idairline` varchar(3) NOT NULL,
	`name` varchar(100) NOT NULL,
	PRIMARY KEY (`idairline`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`airplane` (
	`idairplane` int(11) NOT NULL,
	`fk_idairline` varchar(3) NOT NULL,
	`etat` varchar(45) DEFAULT 'check_OK',
	`seats` int(4) NOT NULL DEFAULT 100,
	PRIMARY KEY (`fk_idairline`,`idairplane`),
	CONSTRAINT `fk_airline` FOREIGN KEY (`fk_idairline`) REFERENCES `BD_AIRPORT`.`airline` (`idairline`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`geographiczone` (
	`idgeographiczone` VARCHAR(10) NOT NULL,
	PRIMARY KEY (`idgeographiczone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`flight` (
	`fk_idairplane` int(11) NOT NULL,
	`fk_idairline` varchar(3) NOT NULL,
	`departure` date NOT NULL,
	`destination` varchar(50) NOT NULL,
	`fk_idgeographiczone` VARCHAR(10) NOT NULL,
	`distance` INT NOT NULL DEFAULT 0,
	`takeOffTime` time DEFAULT NULL,
	`scheduledLanding` time DEFAULT NULL,
	`actualLanding` time DEFAULT NULL,
	`seatsSold` int(4) NOT NULL DEFAULT 0,
	`price` double NOT NULL,
	`piste` varchar(9) DEFAULT NULL,
	PRIMARY KEY (`fk_idairplane`,`fk_idairline`,`departure`,`destination`),
	KEY `fk_airplane` (`fk_idairline`,`fk_idairplane`),
	CONSTRAINT `fk_airplane` FOREIGN KEY (`fk_idairline`,`fk_idairplane`) REFERENCES `BD_AIRPORT`.`airplane` (`fk_idairline`, `idairplane`),
	KEY `fk_geographiczone` (`fk_idgeographiczone`),
	CONSTRAINT `fk_geographiczone` FOREIGN KEY (`fk_idgeographiczone`) REFERENCES `BD_AIRPORT`.`geographiczone` (`idgeographiczone`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`passenger` (
	`idpassenger` varchar(14) NOT NULL,
	`lastname` varchar(30) NOT NULL,
	`birthday` date not null,
	`gender` CHAR(1) NOT NULL,
	`firstname` varchar(30) NOT NULL,
	`login` varchar(30) DEFAULT NULL,
	`password` varchar(50) DEFAULT NULL,
	PRIMARY KEY (`idpassenger`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`caddie` (
	`idcaddie` INT NOT NULL AUTO_INCREMENT,
	`fk_idpassenger` varchar(14) NOT NULL,
	PRIMARY KEY (`idcaddie`),
	KEY `fk_passenger_caddie` (`fk_idpassenger`),
	CONSTRAINT `fk_passenger_caddie` FOREIGN KEY (`fk_idpassenger`) REFERENCES `BD_AIRPORT`.`passenger` (`idpassenger`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`caddieitem`(
	`iditem`  INT NOT NULL AUTO_INCREMENT,
	`fk_idcaddie` INT NOT NULL,
	`fk_idairplane` int(11) NOT NULL,
	`fk_idairline` varchar(3) NOT NULL,
	`fk_departure` date NOT NULL,
	`fk_destination` varchar(50) NOT NULL,
	`reservedSeats` INT(4) NOT NULL DEFAULT 0,
	PRIMARY KEY (`iditem`),
	KEY `fk_caddie` (`fk_idcaddie`),
	CONSTRAINT `fk_caddie` FOREIGN KEY (`fk_idcaddie`) REFERENCES `BD_AIRPORT`.`caddie` (`idcaddie`),
	KEY `ticket_ibfk_2` (`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`),
	CONSTRAINT `ticket_ibfk_2` FOREIGN KEY (`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`) REFERENCES `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`ticket` (
	`fk_idairplane` int(11) NOT NULL,
	`fk_idairline` varchar(3) NOT NULL,
	`fk_departure` date NOT NULL,
	`fk_destination` varchar(50) NOT NULL,
	`idticket` int(11) NOT NULL,
	`fk_idpassenger` varchar(14) NOT NULL,
	`nbaccompagnant` int(11) NOT NULL,
	PRIMARY KEY (`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`,`idticket`),
	KEY `fk_passenger` (`fk_idpassenger`),
	CONSTRAINT `fk_passenger_ticket` FOREIGN KEY (`fk_idpassenger`) REFERENCES `BD_AIRPORT`.`passenger` (`idpassenger`),
	CONSTRAINT `ticket_ibfk_1` FOREIGN KEY (`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`) REFERENCES `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

CREATE TABLE `BD_AIRPORT`.`luggage` (
	`fk_idairplane` int(11) NOT NULL,
	`fk_idairline` varchar(3) NOT NULL,
	`fk_departure` date NOT NULL,
	`fk_destination` varchar(50) NOT NULL,
	`fk_idticket` int(11) NOT NULL,
	`idluggage` int(11) NOT NULL,
	`weight` FLOAT NOT NULL DEFAULT 0.0,
	`isluggage` TINYINT(1) NULL DEFAULT 1,
	`received` CHAR(1) NOT NULL DEFAULT 'N',
	`loaded` CHAR(1) NOT NULL DEFAULT 'N',
	`checkedbycustom` CHAR(1) NOT NULL DEFAULT 'N',
	`comments` varchar(255) NOT NULL DEFAULT 'NEANT',
	PRIMARY KEY (`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`,`fk_idticket`,`idluggage`),
	CONSTRAINT `fk_ticket` FOREIGN KEY (`fk_idairplane`,`fk_idairline`,`fk_departure`,`fk_destination`,`fk_idticket`) REFERENCES `BD_AIRPORT`.`ticket` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `idticket`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
