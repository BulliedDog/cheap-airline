package com.skywings.repository;

import com.skywings.mapper.UtenteMapper;
import com.skywings.model.Utente;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UtenteRepository {

    private final JdbcTemplate jdbcTemplate;

    // Spring inietta automaticamente la connessione al database (JdbcTemplate)
    public UtenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * Cerca un utente nel DB usando il suo username.
     * Restituisce un Optional (una scatola che può essere piena o vuota).
     */
    public Optional<Utente> trovaPerUsername(String username) {
        // La query SQL vera e propria
        String sql = "SELECT * FROM utenti WHERE username = ?";

        try {
            // 1. Esegue la query
            // 2. Usa UtenteMapper per trasformare la riga SQL in oggetto Java
            Utente utente = jdbcTemplate.queryForObject(
                    sql,
                    new UtenteMapper(),
                    username
            );

            return Optional.of(utente); // Trovato!

        } catch (EmptyResultDataAccessException e) {
            // Se il database non trova nulla, non far esplodere tutto,
            // restituisci semplicemente "vuoto".
            return Optional.empty();
        }
    }
}