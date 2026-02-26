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
        String sql = "INSERT INTO utenti (username, email, password, nome, cognome, ruolo) VALUES (?, ?, ?, ?, ?, ?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            // Specifichiamo che vogliamo indietro la chiave generata (l'id SERIAL)
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"id"});
            ps.setString(1, utente.getUsername());
            ps.setString(2, utente.getEmail());
            ps.setString(3, utente.getPassword());
            ps.setString(4, utente.getNome());
            ps.setString(5, utente.getCognome());
            ps.setString(6, utente.getRuolo());
            return ps;
        }, keyHolder);

        // Recuperiamo l'ID generato dal DB e lo iniettiamo nell'oggetto
        if (keyHolder.getKey() != null) {
            utente.setId(keyHolder.getKey().longValue());
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