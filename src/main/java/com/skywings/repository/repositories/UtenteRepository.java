package com.skywings.repository.repositories;

import com.skywings.mapper.UtenteMapper;
import com.skywings.model.Utente;
import com.skywings.repository.interfaces.UtenteDAO;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Optional;

@Repository
public class UtenteRepository implements UtenteDAO { // Implementa l'interfaccia DAO

    private final JdbcTemplate jdbcTemplate;

    public UtenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override // Indica che stiamo rispettando il contratto dell'interfaccia
    public Utente save(Utente utente) {
        String sql = "INSERT INTO utenti (username, email, password, nome, cognome, ruolo) VALUES (?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, utente.getUsername());
            ps.setString(2, utente.getEmail());
            ps.setString(3, utente.getPassword());
            ps.setString(4, utente.getNome());
            ps.setString(5, utente.getCognome());
            ps.setString(6, utente.getRuolo());
            return ps;
        }, keyHolder);

        if (keyHolder.getKeys() != null && keyHolder.getKeys().containsKey("id")) {
            // Estraiamo il valore associato alla colonna "id" generato dal db
            Number generato = (Number) keyHolder.getKeys().get("id");
            utente.setId(generato.longValue());
        }
        return utente;
    }

    @Override
    public Optional<Utente> trovaPerUsername(String username) {
        String sql = "SELECT * FROM utenti WHERE username = ?";
        try {
            Utente utente = jdbcTemplate.queryForObject(sql, new UtenteMapper(), username);
            return Optional.of(utente);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }
}