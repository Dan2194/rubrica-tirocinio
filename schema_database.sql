-- Creazione del database
CREATE DATABASE IF NOT EXISTS rubrica_db;

USE rubrica_db;

-- tabella per gli utenti
CREATE TABLE IF NOT EXISTS utenti (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

-- tabella per le persone
CREATE TABLE IF NOT EXISTS persone (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(50) NOT NULL,
    cognome VARCHAR(50) NOT NULL,
    indirizzo VARCHAR(100),
    telefono VARCHAR(20),
    eta INT
    );

-- utente di test
INSERT INTO utenti (username, password) VALUES ('admin', 'admin');