DELETE FROM `BD_AIRPORT`.`luggage`;
DELETE FROM `BD_AIRPORT`.`ticket`;
DELETE FROM `BD_AIRPORT`.`passenger`;
DELETE FROM `BD_AIRPORT`.`flight`;
DELETE FROM `BD_AIRPORT`.`airplane`;
DELETE FROM `BD_AIRPORT`.`airline`;
DELETE FROM `BD_AIRPORT`.`agent`;
DELETE FROM `BD_AIRPORT`.`job`;

INSERT INTO `BD_AIRPORT`.`job` VALUES(1,'Agent de compagnie aérienne');
INSERT INTO `BD_AIRPORT`.`job` VALUES(2,'Bagagiste');
INSERT INTO `BD_AIRPORT`.`job` VALUES(3,'Employé agréé de tour-operator');
INSERT INTO `BD_AIRPORT`.`job` VALUES(4,'Aiguilleur du ciel');

INSERT INTO `BD_AIRPORT`.`agent` VALUES('laurent', 'password', 'Reynders', 'Laurent', 1);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('oceane', 'password', 'Stasse', 'Océane', 1);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('toto', 'mdp', 'Tata', 'Toto', 2);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('unBagagiste', 'mdp', 'Bag', 'Agiste', 2);

INSERT INTO `BD_AIRPORT`.`airline` VALUES('LH', 'Lufthansa');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('AF', 'Air France');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('SN', 'Brussels Airlines');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('WA', 'WALABIES-AIRLINES');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('PA', 'POWDER-AIRLINES');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('AFC', 'AIR FRANCE CANAILLE');

INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(714, 'WA');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(666, 'WA');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(404, 'WA');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(362, 'PA');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(152, 'AFC');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(252, 'AFC');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(1002, 'LH');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(904, 'LH');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(842, 'LH');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(468, 'LH');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(999, 'LH');

INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('714', 'WA', '2017-10-17', 'Marrakech', '08:02:00', '23:57:00');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('714', 'WA', '2017-10-19', 'Marrakech', '08:02:00', '23:57:00');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('714', 'WA', '2017-10-21', 'Marrakech', '08:02:00', '23:57:00');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', '08:02:00', '23:57:00');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('666', 'WA', '2017-10-23', 'Sydney', '05:30:00', '20:57:00');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('362', 'PA', '2017-10-23', 'Peshawar', '06:30:00', '16:21:00');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `takeOffTime`, `scheduledLanding`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', '07:20:00', '20:57:00');

INSERT INTO `BD_AIRPORT`.`passenger` VALUES('591-2377428-11', 'Vilvens', 'Claude');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('591-6317438-57', 'Charlet', 'Christophe');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('592-8373221-89', 'Gillet', 'Clément');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('592-2377428-41', 'Dreze', 'Morgan');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('592-4397458-51', 'Duchmoll', 'Pierre');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('592-2676491-61', 'Mult', 'Julien');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('592-2327423-71', 'Heinz', 'Ketchup');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('591-1266317-22', 'Ouftinenni', 'André');
INSERT INTO `BD_AIRPORT`.`passenger` VALUES('591-3488539-33', 'Charvilrom', 'Walter');

INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-10-17', 'Marrakech', 070, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-10-19', 'Marrakech', 070, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-10-21', 'Marrakech', 070, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-10-23', 'Marrakech', 070, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-10-23', 'Marrakech', 081, '591-6317438-57', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-10-23', 'Marrakech', 102, '591-1266317-22', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-10-23', 'Paris', 101, '591-3488539-33', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-10-23', 'Paris', 102, '592-2327423-71', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-10-23', 'Paris', 103, '592-2676491-61', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-10-23', 'Paris', 10, '592-8373221-89', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('362', 'PA', '2017-10-23', 'Peshawar', 30, '592-2377428-41', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('362', 'PA', '2017-10-23', 'Peshawar', 40, '592-4397458-51', 0);

INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 102, 001, 15.5, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 102, 002, 19.9, 0);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 081, 001, 13.3, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 070, 001, 25.0, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 070, 002, 19.9, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 070, 003, 19.9, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 070, 004, 14.0, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-10-23', 'Marrakech', 070, 005, 5.0, 0, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 101, 001, 15.5, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 101, 002, 19.9, 0);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 102, 001, 13.3, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 103, 001, 25.0, 1, 'O', 'Ca send fort...');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 103, 002, 19.9, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 103, 003, 19.9, 1, 'O', 'Rien à signaler.');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 101, 004, 14.0, 1, 'O', 'quel border!');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-10-23', 'Paris', 103, 005, 5.0, 0, 'O', 'Pas de cadena réglementaire, on a du le couper.');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('362', 'PA', '2017-10-23', 'Peshawar', 30, 001, 15.5, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('362', 'PA', '2017-10-23', 'Peshawar', 30, 002, 19.9, 0);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('362', 'PA', '2017-10-23', 'Peshawar', 30, 010, 13.3, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('362', 'PA', '2017-10-23', 'Peshawar', 40, 001, 25.0, 1, 'O', 'du sucre en poudre ?!?');
