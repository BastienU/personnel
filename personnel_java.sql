-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : dim. 24 mars 2024 à 16:24
-- Version du serveur : 10.4.27-MariaDB
-- Version de PHP : 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `personnel_java`
--

-- --------------------------------------------------------

--
-- Structure de la table `employe`
--

CREATE TABLE `employe` (
  `idEmploye` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `prenom` varchar(255) DEFAULT NULL,
  `mail` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `dateArrivee` date DEFAULT NULL,
  `dateDepart` date DEFAULT NULL,
  `estAdmin` tinyint(1) DEFAULT NULL,
  `idLigue` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `employe`
--

INSERT INTO `employe` (`idEmploye`, `nom`, `prenom`, `mail`, `password`, `dateArrivee`, `dateDepart`, `estAdmin`, `idLigue`) VALUES
(10, 'toto', 'tata', NULL, NULL, '2023-02-28', NULL, NULL, 260),
(12, 'Moi', 'MoiJE', 'moijesuis@developpeur.fr', 'myDev', '2024-03-21', NULL, NULL, 258),
(14, 'Toi', 'tues', 'tues@unartiste.org', 'Banksy', '2024-03-21', NULL, NULL, 259),
(15, 'Eux', 'Ceuxlà', 'lesautres@fonctionpublic.fr', 'Fainéants', '2024-03-01', '2024-03-24', NULL, 259),
(16, 'titi', 'tati', '', '', '2022-02-22', NULL, NULL, 260),
(35, 'Toor', 'Michel', '', 'Mitoor', NULL, NULL, NULL, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `ligue`
--

CREATE TABLE `ligue` (
  `idLigue` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `idEmploye` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `ligue`
--

INSERT INTO `ligue` (`idLigue`, `nom`, `idEmploye`) VALUES
(258, 'France', NULL),
(259, 'Espagne', NULL),
(260, 'Angleterre', NULL);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `employe`
--
ALTER TABLE `employe`
  ADD PRIMARY KEY (`idEmploye`),
  ADD KEY `idLigue` (`idLigue`);

--
-- Index pour la table `ligue`
--
ALTER TABLE `ligue`
  ADD PRIMARY KEY (`idLigue`),
  ADD KEY `ligue_ibfk_1` (`idEmploye`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `employe`
--
ALTER TABLE `employe`
  MODIFY `idEmploye` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=36;

--
-- AUTO_INCREMENT pour la table `ligue`
--
ALTER TABLE `ligue`
  MODIFY `idLigue` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=267;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `employe`
--
ALTER TABLE `employe`
  ADD CONSTRAINT `employe_ibfk_1` FOREIGN KEY (`idLigue`) REFERENCES `ligue` (`idLigue`) ON DELETE CASCADE;

--
-- Contraintes pour la table `ligue`
--
ALTER TABLE `ligue`
  ADD CONSTRAINT `ligue_ibfk_1` FOREIGN KEY (`idEmploye`) REFERENCES `employe` (`idEmploye`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
