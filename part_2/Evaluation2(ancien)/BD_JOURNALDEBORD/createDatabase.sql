DROP TABLE ACTIVITES CASCADE CONSTRAINTS;
DROP TABLE INTERVENANTS CASCADE CONSTRAINTS;


CREATE TABLE intervenants(
	id NUMBER CONSTRAINT pk_intervenants PRIMARY KEY,
	status VARCHAR2(100),
	name VARCHAR2(100) NOT NULL
);

CREATE TABLE activites(
	id NUMBER CONSTRAINT pk_activites PRIMARY KEY,
    dateActivite TIMESTAMP,
	typeActivite VARCHAR2(100),
	description VARCHAR2(255),
	intervenant NUMBER CONSTRAINT fk_activites_intervenants REFERENCES INTERVENANTS(id)
);
