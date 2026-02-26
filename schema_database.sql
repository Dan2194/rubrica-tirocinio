-- Creazione del database
CREATE DATABASE IF NOT EXISTS rubrica_db;

-- Selezione del database da utilizzare
USE rubrica_db;

-- Creazione della tabella per gli utenti
CREATE TABLE IF NOT EXISTS utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

-- Creazione della tabella per le persone
CREATE TABLE IF NOT EXISTS persone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    indirizzo VARCHAR(100),
    telefono VARCHAR(20),
    eta INT
    );

-- Inserimento di un utente di test per permettere il primo accesso al software
INSERT INTO utenti (username, password) VALUES ('admin', 'admin');