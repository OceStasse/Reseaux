DELETE FROM `BD_AIRPORT`.`postes`;
DELETE FROM `BD_AIRPORT`.`agents`;
DELETE FROM `BD_AIRPORT`.`avions`;
DELETE FROM `BD_AIRPORT`.`vols`;
DELETE FROM `BD_AIRPORT`.`statusBagage`;
DELETE FROM `BD_AIRPORT`.`passagers`;
DELETE FROM `BD_AIRPORT`.`billets`;
DELETE FROM `BD_AIRPORT`.`baggages`;


INSERT INTO `BD_AIRPORT`.`postes` VALUES(1, 'Agent de compagnie aérienne');
INSERT INTO `BD_AIRPORT`.`postes` VALUES(2, 'bagagiste');
INSERT INTO `BD_AIRPORT`.`postes` VALUES(3, 'employé agréé de tour-operator');
INSERT INTO `BD_AIRPORT`.`postes` VALUES(4, 'aiguilleur du ciel');


INSERT INTO `BD_AIRPORT`.`agents` VALUES('laurent', 'password', 'Rouffart', 'Nicolas', 1);
INSERT INTO `BD_AIRPORT`.`agents` VALUES('oceane', 'password', 'Stasse', 'Océane', 1);
INSERT INTO `BD_AIRPORT`.`agents` VALUES('toto', 'mdp', 'Tata', 'Toto', 2);


INSERT INTO `BD_AIRPORT`.`avions` VALUES(1, '747', 'check_OK');
INSERT INTO `BD_AIRPORT`.`avions` VALUES(2, '362', 'check_OK');
INSERT INTO `BD_AIRPORT`.`avions` VALUES(3, '777', 'check_OK');
INSERT INTO `BD_AIRPORT`.`avions` VALUES(4, '417', 'check_OK');


INSERT INTO `BD_AIRPORT`.`vols` VALUES(1, '2017-10-10', 'Peshawar', NOW(), NOW(), NOW(), 'BrusselsAirlines');
INSERT INTO `BD_AIRPORT`.`vols` VALUES(2, '2017-10-15', 'Peshawar', NOW(), NOW(), NOW(), 'POWDER-AIRLINES');


INSERT INTO `BD_AIRPORT`.`statusBagage` VALUES(1, 'Réceptionné');
INSERT INTO `BD_AIRPORT`.`statusBagage` VALUES(2, 'Chargé en soute');
INSERT INTO `BD_AIRPORT`.`statusBagage` VALUES(3, 'Vérifié par la douane');


INSERT INTO `BD_AIRPORT`.`passagers` VALUES('592-6495224-88', 'Rouffart', 'Nicolas');
INSERT INTO `BD_AIRPORT`.`passagers` VALUES('592-1234567-88', 'Tata', 'Toto');
INSERT INTO `BD_AIRPORT`.`passagers` VALUES('592-7654321-88', 'Stasse', 'Oceane');


INSERT INTO `BD_AIRPORT`.`billets` VALUES('362-22082017-0070', '592-6495224-88', 0);
INSERT INTO `BD_AIRPORT`.`billets` VALUES('362-22082017-0080', '592-1234567-88', 10);