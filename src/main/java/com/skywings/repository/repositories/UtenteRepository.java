package com.skywings.repository.repositories;

import com.skywings.model.Utente;
import com.skywings.repository.interfaces.UtenteDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
public class UtenteRepository implements UtenteDAO {

    private final JdbcTemplate jdbcTemplate;

    public UtenteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Utente> utenteMapper = (rs, rowNum) -> {
        Utente utente = new Utente();
        utente.setId(rs.getLong("id"));
        utente.setUsername(rs.getString("username")); // Mappato
        utente.setEmail(rs.getString("email"));
        utente.setPassword(rs.getString("password"));
        utente.setNome(rs.getString("nome"));
        utente.setCognome(rs.getString("cognome"));   // Mappato
        utente.setRuolo(rs.getString("ruolo"));
        return utente;
    };

    @Override
    public Optional<Utente> findByUsername(String username) {
        String sql = "SELECT * FROM utenti WHERE username = ?";
        return jdbcTemplate.query(sql, utenteMapper, username).stream().findFirst();
    }

    @Override
    public Utente save(Utente utente) {
        if (utente.getId() != null && utente.getId() > 0) {
            // È UN UPDATE (Modifica)
            String sql = "UPDATE utenti SET username = ?, email = ?, password = ?, nome = ?, cognome = ?, ruolo = ? WHERE id = ?";
            jdbcTemplate.update(sql,
                    utente.getUsername(),
                    utente.getEmail(),
                    utente.getPassword(),
                    utente.getNome(),
                    utente.getCognome(),
                    utente.getRuolo(),
                    utente.getId()
            );
        } else {
            // È UN INSERT (Nuovo record)
            String sql = "INSERT INTO utenti (username, email, password, nome, cognome, ruolo) VALUES (?, ?, ?, ?, ?, ?)";
            jdbcTemplate.update(sql,
                    utente.getUsername(),
                    utente.getEmail(),
                    utente.getPassword(),
                    utente.getNome(),
                    utente.getCognome(),
                    utente.getRuolo()
            );
        }
        return utente;
    }

    @Override
    public List<Utente> findAll() {
        String sql = "SELECT * FROM utenti";
        return jdbcTemplate.query(sql, utenteMapper);
    }

    @Override
    public Optional<Utente> findById(Long id) {
        String sql = "SELECT * FROM utenti WHERE id = ?";
        return jdbcTemplate.query(sql, utenteMapper, id).stream().findFirst();
    }

    @Override
    public Optional<Utente> findByEmail(String email) {
        String sql = "SELECT * FROM utenti WHERE email = ?";
        return jdbcTemplate.query(sql, utenteMapper, email).stream().findFirst();
    }

    @Override
    public void update(Utente utente) {
        String sql = "UPDATE utenti SET nome = ?, email = ?, password = ?, ruolo = ? WHERE id = ?";
        jdbcTemplate.update(sql, utente.getNome(), utente.getEmail(), utente.getPassword(), utente.getRuolo(), utente.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM utenti WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}