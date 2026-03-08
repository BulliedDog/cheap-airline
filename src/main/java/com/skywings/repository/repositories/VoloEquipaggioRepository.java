package com.skywings.repository.repositories;

import com.skywings.model.VoloEquipaggio;
import com.skywings.dto.VoloEquipaggioDTO;
import com.skywings.repository.interfaces.VoloEquipaggioDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoloEquipaggioRepository implements VoloEquipaggioDAO {

    private final JdbcTemplate jdbcTemplate;

    public VoloEquipaggioRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<VoloEquipaggio> equipaggioMapper = (rs, rowNum) -> {
        VoloEquipaggio ve = new VoloEquipaggio();
        ve.setIdVolo(rs.getLong("id_volo"));
        ve.setIdUtente(rs.getLong("id_utente"));
        return ve;
    };

    @Override
    public List<VoloEquipaggio> findAll() {
        // CORRETTO: equipaggio_volo
        return jdbcTemplate.query("SELECT * FROM equipaggio_volo", (rs, rowNum) -> {
            VoloEquipaggio ve = new VoloEquipaggio();
            ve.setIdVolo(rs.getLong("id_volo"));
            ve.setIdUtente(rs.getLong("id_utente"));
            // Se hai il campo nel modello: ve.setNote(rs.getString("note_assegnazione"));
            return ve;
        });
    }

    @Override
    public List<VoloEquipaggio> findByVoloId(Long idVolo) {
        return jdbcTemplate.query("SELECT * FROM equipaggio_volo WHERE id_volo = ?", equipaggioMapper, idVolo);
    }

    @Override
    public void save(VoloEquipaggio ve) {
        // Controlliamo se questa assegnazione Volo-Utente esiste già
        String checkSql = "SELECT COUNT(*) FROM equipaggio_volo WHERE id_volo = ? AND id_utente = ?";
        Integer count = jdbcTemplate.queryForObject(checkSql, Integer.class, ve.getIdVolo(), ve.getIdUtente());

        if (count != null && count > 0) {
            // È UN UPDATE (Modifica)
            // Se nella tua tabella hai un campo extra come 'note_assegnazione', fai l'update qui.
            // Se hai SOLO le chiavi, l'update è superfluo perché le chiavi non cambiano,
            // ma ecco il codice predisposto se lo usi:
            String updateSql = "UPDATE equipaggio_volo SET note_assegnazione = ? WHERE id_volo = ? AND id_utente = ?";
            jdbcTemplate.update(updateSql, ve.getNoteAssegnazione(), ve.getIdVolo(), ve.getIdUtente());
        } else {
            // È UN INSERT (Nuovo membro aggiunto all'equipaggio del volo)
            String insertSql = "INSERT INTO equipaggio_volo (id_volo, id_utente, note_assegnazione) VALUES (?, ?, ?)";
            jdbcTemplate.update(insertSql, ve.getIdVolo(), ve.getIdUtente(), ve.getNoteAssegnazione());
        }
    }

    @Override
    public void delete(Long idVolo, Long idUtente) {
        jdbcTemplate.update("DELETE FROM equipaggio_volo WHERE id_volo = ? AND id_utente = ?", idVolo, idUtente);
    }

    @Override
    public List<VoloEquipaggio> findByUtenteId(Long idUtente) {
        String sql = "SELECT * FROM equipaggio_volo WHERE id_utente = ?";
        return jdbcTemplate.query(sql, equipaggioMapper, idUtente);
    }

    public List<VoloEquipaggioDTO> findAllDettagli() {
        String sql = """
        SELECT ev.id_volo, ev.id_utente, v.codice_volo, u.nome, u.cognome, u.ruolo 
        FROM equipaggio_volo ev
        JOIN voli v ON ev.id_volo = v.id
        JOIN utenti u ON ev.id_utente = u.id
    """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            VoloEquipaggioDTO dto = new VoloEquipaggioDTO();
            dto.setIdVolo(rs.getLong("id_volo"));
            dto.setIdUtente(rs.getLong("id_utente"));
            dto.setCodiceVolo(rs.getString("codice_volo"));
            dto.setNomeMembro(rs.getString("nome"));
            dto.setCognomeMembro(rs.getString("cognome"));
            dto.setRuoloMembro(rs.getString("ruolo"));
            return dto;
        });
    }

    @Override
    public VoloEquipaggio findByIds(Long idVolo, Long idUtente) {
        String sql = "SELECT * FROM equipaggio_volo WHERE id_volo = ? AND id_utente = ?";

        // Usiamo l'equipaggioMapper che hai già definito in alto
        List<VoloEquipaggio> risultati = jdbcTemplate.query(sql, equipaggioMapper, idVolo, idUtente);

        // Se la lista è vuota ritorna null, altrimenti il primo risultato
        return risultati.isEmpty() ? null : risultati.get(0);
    }

}