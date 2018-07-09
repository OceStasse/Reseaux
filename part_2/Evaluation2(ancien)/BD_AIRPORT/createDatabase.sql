-- CREATE SCHEMA `BD_AIRPORT` DEFAULT CHARACTER SET utf8 ;
-- On efface les tables si elles existent
DROP TABLE IF EXISTS `BD_AIRPORT`.`baggages`,
					 `BD_AIRPORT`.`billets`,
					 `BD_AIRPORT`.`passagers`,
					 `BD_AIRPORT`.`statusBagage`,
					 `BD_AIRPORT`.`vols`,
					 `BD_AIRPORT`.`avions`,
					 `BD_AIRPORT`.`agents`,
					 `BD_AIRPORT`.`postes`;

CREATE TABLE `BD_AIRPORT`.`postes`(
	`id` INT NOT NULL,
	`name` VARCHAR(100) NOT NULL,
	PRIMARY KEY (`id`)
);

CREATE TABLE `BD_AIRPORT`.`agents`(
	`login` VARCHAR(50) NOT NULL,
	`password` VARCHAR(50) NOT NULL,
	`name` VARCHAR(20) DEFAULT NULL,
	`firstName` VARCHAR(20) DEFAULT NULL,
	`idPoste` INT,
	PRIMARY KEY (`login`),
	FOREIGN KEY (`idPoste`) REFERENCES `BD_AIRPORT`.`postes`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `BD_AIRPORT`.`avions`(
	`id` INT NOT NULL,
	`modele` VARCHAR(100) NOT NULL,
	`etat` VARCHAR(50) DEFAULT 'check_OK',
	PRIMARY KEY(`id`)
);

CREATE TABLE `BD_AIRPORT`.`vols`(
	`idAvion` INT NOT NULL,
	`date` DATE, 
	`destination` VARCHAR(50) NOT NULL,
	`heureDepart` TIME,
	`heureArriveeTheorique` TIME,
	`heureArriveeReelle` TIME,
	`airline` VARCHAR(100),
	PRIMARY KEY (`idAvion`, `date`, `destination`),
	FOREIGN KEY (`idAvion`) REFERENCES `BD_AIRPORT`.`avions`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);


CREATE TABLE `BD_AIRPORT`.`statusBagage`(
	`id` INT NOT NULL,
	`name` VARCHAR(50),
	PRIMARY KEY (`id`)
);

CREATE TABLE `BD_AIRPORT`.`passagers`(
	`idCardNumber` VARCHAR(14) NOT NULL,
	`name` VARCHAR(20) DEFAULT NULL,
	`firstName` VARCHAR(20),
	PRIMARY KEY (`idCardNumber`)
);

CREATE TABLE `BD_AIRPORT`.`billets`(
	`id` VARCHAR(17) NOT NULL,
	`idPassager` VARCHAR(14) NOT NULL,
	`nbAccompagnants` INT,
	PRIMARY KEY (`id`),
	FOREIGN KEY (`idPassager`) REFERENCES `BD_AIRPORT`.`passagers`(`idCardNumber`) ON DELETE CASCADE ON UPDATE CASCADE
);

CREATE TABLE `BD_AIRPORT`.`baggages`(
	`id` VARCHAR(50) NOT NULL,
	`idVol` INT NOT NULL,
	`idBillet` VARCHAR(17) NOT NULL,
	`status` INT NOT NULL,
	`commentaires` VARCHAR(255),
	PRIMARY KEY (`id`),
	FOREIGN KEY (`idVol`) REFERENCES `BD_AIRPORT`.`vols`(`idAvion`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`idBillet`) REFERENCES `BD_AIRPORT`.`billets`(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
	FOREIGN KEY (`status`) REFERENCES `BD_AIRPORT`.`statusBagage`(`id`) ON DELETE CASCADE ON UPDATE CASCADE
);


