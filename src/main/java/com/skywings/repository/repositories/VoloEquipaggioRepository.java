package com.skywings.repository.repositories;

import com.skywings.mapper.VoloEquipaggioMapper;
import com.skywings.model.VoloEquipaggio;
import com.skywings.model.VoloEquipaggioDTO;
import com.skywings.repository.interfaces.VoloEquipaggioDAO;
import org.springframework.beans.factory.annotation.Autowired;
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
        return jdbcTemplate.query("SELECT * FROM volo_equipaggio WHERE id_volo = ?", equipaggioMapper, idVolo);
    }

    @Override
    public void save(VoloEquipaggio ve) {
        String sql = "INSERT INTO volo_equipaggio (id_volo, id_utente) VALUES (?, ?)";
        jdbcTemplate.update(sql, ve.getIdVolo(), ve.getIdUtente());
    }

    @Override
    public void delete(Long idVolo, Long idUtente) {
        jdbcTemplate.update("DELETE FROM volo_equipaggio WHERE id_volo = ? AND id_utente = ?", idVolo, idUtente);
    }

    @Override
    public List<VoloEquipaggio> findByUtenteId(Long idUtente) {
        String sql = "SELECT * FROM volo_equipaggio WHERE id_utente = ?";
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
}