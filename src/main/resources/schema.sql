CREATE TABLE utenti (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL, -- NUOVO CAMPO PER IL LOGIN
    email VARCHAR(100) UNIQUE NOT NULL,   -- Resta per contatti
    password VARCHAR(255) NOT NULL,
    nome VARCHAR(100) NOT NULL,
    cognome VARCHAR(100) NOT NULL,
    ruolo VARCHAR(20) NOT NULL CHECK (ruolo IN ('CLIENTE', 'ADMIN', 'PILOTA', 'HOSTESS'))
);

INSERT INTO utenti (username, email, password, nome_completo, ruolo)
VALUES
('admin_boss', 'admin@skywings.it', 'admin123', 'Capo Supremo', 'ADMIN'),
('supermario', 'mario@email.it', 'cliente123', 'Mario Rossi', 'CLIENTE'),
('maverick', 'pilota@skywings.it', 'volo123', 'Maverick', 'PILOTA')
ON CONFLICT (username) DO NOTHING;