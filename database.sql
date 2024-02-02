CREATE DATABASE Projet;

CREATE TABLE EMPLOYE
(
    id_employe INT PRIMARY KEY NOT NULL,
    nom_employe VARCHAR(100),
    prenom_employe VARCHAR(100),
    mail_employe VARCHAR(100) ,
    password_employe VARCHAR(100),
    admin VARCHAR(100),
    date_arrivee DATE,
    date_depart DATE
);

CREATE TABLE LIGUE
(
    id_ligue INT PRIMARY KEY NOT NULL,
    nom_ligue
);

ALTER TABLE EMPLOYE
ADD FOREIGN KEY id_ligue REFERENCES LIGUE;

ALTER TABLE EMPLOYE
FOREIGN KEY (id_ligue)
REFERENCES LIGUE(id_ligue);

ALTER TABLE LIGUE
ADD COLUMN id_employe INT;

ALTER TABLE LIGUE
FOREIGN KEY (id_employe)
REFERENCES EMPLOYE(id_employe);
