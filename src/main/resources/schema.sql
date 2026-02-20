CREATE TABLE IF NOT EXISTS utenti(
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL, -- NUOVO CAMPO PER IL LOGIN
    email VARCHAR(100) UNIQUE NOT NULL,   -- Resta per contatti
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    ruolo VARCHAR(20) NOT NULL CHECK (ruolo IN ('CLIENTE', 'ADMIN', 'PILOTA', 'HOSTESS'))
);

CREATE TABLE IF NOT EXISTS citta (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    nazione VARCHAR(100) NOT NULL,
    codice_iata VARCHAR(3) UNIQUE NOT NULL
);

CREATE TABLE IF NOT EXISTS aerei (
    id SERIAL PRIMARY KEY,
    modello VARCHAR(100) NOT NULL,
    produttore VARCHAR(100) NOT NULL,
    capacita_economy INT NOT NULL CHECK (capacita_economy >= 0),
    capacita_business INT NOT NULL CHECK (capacita_business >= 0)
);

CREATE TABLE IF NOT EXISTS voli (
    id SERIAL PRIMARY KEY,
    codice_volo VARCHAR(10) UNIQUE NOT NULL,
    id_citta_partenza INT NOT NULL REFERENCES citta(id) ON DELETE RESTRICT,
    id_citta_arrivo INT NOT NULL REFERENCES citta(id) ON DELETE RESTRICT,
    id_aereo INT NOT NULL REFERENCES aerei(id) ON DELETE RESTRICT,
    orario_partenza TIMESTAMP NOT NULL,
    orario_arrivo TIMESTAMP NOT NULL,
    prezzo_base DECIMAL(10, 2) NOT NULL CHECK (prezzo_base >= 0),
    stato VARCHAR(20) NOT NULL DEFAULT 'PROGRAMMATO' CHECK (stato IN ('PROGRAMMATO', 'IN_VOLO', 'ATTERRATO', 'CANCELLATO', 'IN_RITARDO')),

    CONSTRAINT chk_orari CHECK (orario_arrivo > orario_partenza),
    CONSTRAINT chk_citta_diverse CHECK (id_citta_partenza != id_citta_arrivo)
);

CREATE TABLE IF NOT EXISTS equipaggio_volo (
    id_volo INT NOT NULL REFERENCES voli(id) ON DELETE CASCADE,
    id_utente INT NOT NULL REFERENCES utenti(id) ON DELETE CASCADE,
    note_assegnazione VARCHAR(255), -- Es: "Capo cabina", "Primo Ufficiale"
    PRIMARY KEY (id_volo, id_utente)
);