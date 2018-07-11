DELETE FROM activites;
DELETE FROM intervenants;

INSERT INTO intervenants VALUES(1, 'Professeur', 'Charlet');

INSERT INTO intervenants VALUES(2, 'Professeur', 'Vilvens');

INSERT INTO intervenants VALUES(3, 'Professeur', 'Romio');

INSERT INTO intervenants VALUES(4, 'Etudiant', 'Rouffart');

INSERT INTO intervenants VALUES(5, 'Etudiant', 'Stasse');

INSERT INTO intervenants VALUES(6, 'Etudiant', 'Gillet');

INSERT INTO intervenants VALUES(7, 'Etudiant', 'Gardier');


INSERT INTO activites
VALUES(1, TO_DATE( '08/10/2017 08:00:00 PM', 'MM/DD/YYYY HH:MI:SS AM'), 'Vérification', 'Dernière vérification avant l''évaluation de demain', 5);

INSERT INTO activites
VALUES(2, TO_DATE( '08/10/2017 08:00:00 PM', 'MM/DD/YYYY HH:MI:SS AM'), 'Vérification', 'Dernière vérification avant l''évaluation de demain', 4);

INSERT INTO activites
VALUES(3, TO_DATE( '06/10/2017 06:34:21 PM', 'MM/DD/YYYY HH:MI:SS AM'), 'Creation', 'Création de la base de données d''Oracle', 4);

INSERT INTO activites
VALUES(4, TO_DATE( '03/10/2017 01:10:00 PM', 'MM/DD/YYYY HH:MI:SS AM'), 'Evaluation 1', 'Vérification du travail pour l''évaluation 1', 1);

INSERT INTO activites
VALUES(5, TO_DATE( '03/10/2017 01:10:00 PM', 'MM/DD/YYYY HH:MI:SS AM'), 'Evaluation 1', 'Vérification du travail pour l''évaluation 1', 4);

COMMIT;