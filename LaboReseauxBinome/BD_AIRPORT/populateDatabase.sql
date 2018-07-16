DELETE FROM `BD_AIRPORT`.`luggage`;
DELETE FROM `BD_AIRPORT`.`ticket`;
DELETE FROM `BD_AIRPORT`.`caddieitem`;
DELETE FROM `BD_AIRPORT`.`caddie`;
DELETE FROM `BD_AIRPORT`.`passenger`;
DELETE FROM `BD_AIRPORT`.`flight`;
DELETE FROM `BD_AIRPORT`.`geographiczone`;
DELETE FROM `BD_AIRPORT`.`airplane`;
DELETE FROM `BD_AIRPORT`.`airline`;
DELETE FROM `BD_AIRPORT`.`agent`;
DELETE FROM `BD_AIRPORT`.`job`;

INSERT INTO `BD_AIRPORT`.`job` VALUES(1,'Agent de compagnie aérienne');
INSERT INTO `BD_AIRPORT`.`job` VALUES(2,'Bagagiste');
INSERT INTO `BD_AIRPORT`.`job` VALUES(3,'Employé agréé de tour-operator');
INSERT INTO `BD_AIRPORT`.`job` VALUES(4,'Aiguilleur du ciel');
INSERT INTO `BD_AIRPORT`.`job` VALUES(5,'Analyste');

INSERT INTO `BD_AIRPORT`.`agent` VALUES('laurent', 'password', 'Reynders', 'Laurent', 1);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('oceane', 'password', 'Stasse', 'Océane', 1);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('toto', 'mdp', 'Tata', 'Toto', 2);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('bagagiste', 'mdp', 'Bag', 'Agiste', 2);
INSERT INTO `BD_AIRPORT`.`agent` VALUES('analyste', 'mdp', 'Ana', 'lyste', 5);

INSERT INTO `BD_AIRPORT`.`airline` VALUES('LH', 'Lufthansa');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('AF', 'Air France');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('SN', 'Brussels Airlines');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('WA', 'WALABIES-AIRLINES');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('PA', 'POWDER-AIRLINES');
INSERT INTO `BD_AIRPORT`.`airline` VALUES('AFC', 'AIR FRANCE CANAILLE');

INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(714, 'WA', 125);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(666, 'WA', 101);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(404, 'WA', 230);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(362, 'PA', 130);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(152, 'AFC', 100);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(252, 'AFC', 125);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(1002, 'LH', 150);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(904, 'LH', 175);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`, `seats`) VALUES(842, 'LH', 160);
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(468, 'LH');
INSERT INTO `BD_AIRPORT`.`airplane` (`idairplane`, `fk_idairline`) VALUES(999, 'LH');

INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('EUR');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('AM-N');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('AM-S');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('AS');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('AFR-N');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('AFR-SUBSAH');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('AUST');
INSERT INTO `BD_AIRPORT`.`geographiczone` VALUES ('OCEA');

INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('714', 'WA', '2017-11-17', 'Marrakech', 'AFR-N', 842, '08:02:00', '23:57:00', 1, 163, '1');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('714', 'WA', '2017-11-19', 'Marrakech', 'AFR-N', 842, '08:02:00', '23:57:00', 1, 163, '2');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('714', 'WA', '2017-11-21', 'Marrakech', 'AFR-N', 842, '08:02:00', '23:57:00', 1, 163, '3');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 'AFR-N', 842, '08:02:00', '23:57:00', 3, 163, '4');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('666', 'WA', '2017-11-29', 'Sydney', 'AUST', 4000, '05:30:00', '20:57:00', 0, 936, '5');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('362', 'PA', '2017-11-29', 'Peshawar', 'AS', 1000, '22:30:00', '16:21:00', 2, 918, '6');
INSERT INTO `BD_AIRPORT`.`flight` (`fk_idairplane`, `fk_idairline`, `departure`, `destination`, `fk_idgeographiczone`, `distance`, `takeOffTime`, `scheduledLanding`, `seatsSold`, `price`, `piste`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 'EUR', 400, '23:00:00', '20:57:00', 4, 89, '7');

INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('591-2377428-11', 'Vilvens', 'Claude', '1960-09-10', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('591-6317438-57', 'Charlet', 'Christophe', '1969-02-26', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('592-8373221-89', 'Gillet', 'Clément', '1995-09-18', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('592-2377428-41', 'Dreze', 'Morgan', '1987-05-14', 'F');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('592-4397458-51', 'Duchmoll', 'Pierre', '1970-01-01', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('592-2676491-61', 'Mult', 'Julien', '1999-12-31', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('592-2327423-71', 'Heinz', 'Ketchup','1959-02-01', 'F');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('591-1266317-22', 'Ouftinenni', 'André', '1965-05-05', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`) VALUES('591-3488539-33', 'Charvilrom', 'Walter', '1970-07-07', 'M');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`, `login`, `password`) VALUES('592-3287529-34', 'Stasse', 'Oceane', '1996-10-21', 'F', 'oc', 'mdp');
INSERT INTO `BD_AIRPORT`.`passenger` (`idpassenger`, `lastname`, `firstname`, `birthday`, `gender`, `login`, `password`) VALUES('593-3885549-35', 'Reynders', 'Laurent', '1988-07-26', 'M', 'lolo', 'password');

INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-11-17', 'Marrakech', 001, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-11-19', 'Marrakech', 001, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-11-21', 'Marrakech', 001, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-11-29', 'Marrakech', 001, '591-2377428-11', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-11-29', 'Marrakech', 002, '591-6317438-57', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('714', 'WA', '2017-11-29', 'Marrakech', 003, '591-1266317-22', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-11-29', 'Paris', 001, '591-3488539-33', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-11-29', 'Paris', 002, '592-2327423-71', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-11-29', 'Paris', 003, '592-2676491-61', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('152', 'AFC', '2017-11-29', 'Paris', 004, '592-8373221-89', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('362', 'PA', '2017-11-29', 'Peshawar', 001, '592-2377428-41', 0);
INSERT INTO `BD_AIRPORT`.`ticket` VALUES('362', 'PA', '2017-11-29', 'Peshawar', 002, '592-4397458-51', 0);

INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 003, 001, 15.5, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 003, 002, 19.9, 0);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 002, 001, 13.3, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 001, 001, 25.0, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 001, 002, 19.9, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 001, 003, 19.9, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 001, 004, 14.0, 1, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('714', 'WA', '2017-11-29', 'Marrakech', 001, 005, 5.0, 0, 'O', 'du sucre en poudre ?!?');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 001, 001, 15.5, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 001, 002, 19.9, 0);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 002, 001, 13.3, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 003, 001, 25.0, 1, 'O', 'Ca send fort...');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 003, 002, 19.9, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 003, 003, 19.9, 1, 'O', 'Rien à signaler.');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 001, 004, 14.0, 1, 'O', 'quel border!');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('152', 'AFC', '2017-11-29', 'Paris', 003, 005, 5.0, 0, 'O', 'Pas de cadena réglementaire, on a du le couper.');
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('362', 'PA', '2017-11-29', 'Peshawar', 001, 001, 15.5, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('362', 'PA', '2017-11-29', 'Peshawar', 001, 002, 19.9, 0);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`)
	VALUES('362', 'PA', '2017-11-29', 'Peshawar', 001, 010, 13.3, 1);
INSERT INTO `BD_AIRPORT`.`luggage` (`fk_idairplane`, `fk_idairline`, `fk_departure`, `fk_destination`, `fk_idticket`, `idluggage`, `weight`, `isluggage`, `checkedbycustom`, `comments`)
	VALUES('362', 'PA', '2017-11-29', 'Peshawar', 002, 001, 25.0, 1, 'O', 'du sucre en poudre ?!?');
