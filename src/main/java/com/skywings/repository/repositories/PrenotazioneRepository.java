package com.skywings.repository.repositories;

import com.skywings.model.Prenotazione;
import com.skywings.repository.interfaces.PrenotazioneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PrenotazioneRepository implements PrenotazioneDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private final RowMapper<Prenotazione> rowMapper = (rs, rowNum) -> {
        Prenotazione p = new Prenotazione();
        p.setId(rs.getLong("id"));
        p.setVoloId(rs.getLong("volo_id"));
        p.setUtenteId(rs.getLong("utente_id"));
        p.setNomePasseggero(rs.getString("nome_passeggero"));
        p.setCognomePasseggero(rs.getString("cognome_passeggero"));
        p.setNumeroDocumento(rs.getString("numero_documento"));
        if (rs.getTimestamp("data_prenotazione") != null) {
            p.setDataPrenotazione(rs.getTimestamp("data_prenotazione").toLocalDateTime());
        }
        p.setPrezzoAcquistato(rs.getBigDecimal("prezzo_acquistato"));
        p.setPosto(rs.getString("posto"));
        return p;
    };

    @Override
    public void save(Prenotazione prenotazione) {
        if (prenotazione.getId() != null && prenotazione.getId() > 0) {
            // È UN UPDATE
            String sql = """
                UPDATE prenotazioni 
                SET id_volo = ?, id_utente = ?, nome_passeggero = ?, cognome_passeggero = ?, 
                    posto = ?, classe = ?, prezzo_acquistato = ?,
                WHERE id = ?
            """;
            jdbcTemplate.update(sql,
                    prenotazione.getVoloId(),
                    prenotazione.getUtenteId(),
                    prenotazione.getNomePasseggero(),
                    prenotazione.getCognomePasseggero(),
                    prenotazione.getPosto(),
                    prenotazione.getClasse(),
                    prenotazione.getPrezzoAcquistato(),
                    prenotazione.getId()
            );
        } else {
            // È UN INSERT
            String sql = """
                INSERT INTO prenotazioni 
                (id_volo, id_utente, nome_passeggero, cognome_passeggero, posto, classe, prezzo_acquistato) 
                VALUES (?, ?, ?, ?, ?, ?, ?, ?)
            """;
            jdbcTemplate.update(sql,
                    prenotazione.getVoloId(),
                    prenotazione.getUtenteId(),
                    prenotazione.getNomePasseggero(),
                    prenotazione.getCognomePasseggero(),
                    prenotazione.getPosto(),
                    prenotazione.getClasse(),
                    prenotazione.getPrezzoAcquistato()
            );
        }
    }

    @Override
    public List<Prenotazione> findByUtenteId(Long utenteId) {
        String sql = "SELECT * FROM prenotazioni WHERE utente_id = ?";
        return jdbcTemplate.query(sql, rowMapper, utenteId);
    }

    @Override
    public List<Prenotazione> findByVoloId(Long voloId) {
        String sql = "SELECT * FROM prenotazioni WHERE volo_id = ?";
        return jdbcTemplate.query(sql, rowMapper, voloId);
    }

    @Override
    public List<Prenotazione> findAll() {
        String sql = "SELECT * FROM prenotazioni ORDER BY id DESC"; // Le più recenti prima
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public Prenotazione findById(Long id) {
        String sql = "SELECT * FROM prenotazioni WHERE id = ?";
        List<Prenotazione> risultati = jdbcTemplate.query(sql, rowMapper, id);
        return risultati.isEmpty() ? null : risultati.get(0);
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM prenotazioni WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}