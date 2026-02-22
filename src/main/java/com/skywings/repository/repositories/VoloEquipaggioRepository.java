package com.skywings.repository.repositories;

import com.skywings.mapper.VoloEquipaggioMapper;
import com.skywings.model.VoloEquipaggio;
import com.skywings.repository.interfaces.VoloEquipaggioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class VoloEquipaggioRepository implements VoloEquipaggioDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private VoloEquipaggioMapper voloEquipaggioMapper;

    @Override
    public List<VoloEquipaggio> findAll() {
        String sql = "SELECT * FROM volo_equipaggio";
        return jdbcTemplate.query(sql, voloEquipaggioMapper);
    }

    @Override
    public List<VoloEquipaggio> findByVoloId(Long idVolo) {
        String sql = "SELECT * FROM volo_equipaggio WHERE id_volo = ?";
        return jdbcTemplate.query(sql, voloEquipaggioMapper, idVolo);
    }

    @Override
    public List<VoloEquipaggio> findByUtenteId(Long idUtente) {
        String sql = "SELECT * FROM volo_equipaggio WHERE id_utente = ?";
        return jdbcTemplate.query(sql, voloEquipaggioMapper, idUtente);
    }

    @Override
    public void save(VoloEquipaggio ve) {
        String sql = "INSERT INTO volo_equipaggio (id_volo, id_utente, note_assegnazione) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, ve.getIdVolo(), ve.getIdUtente(), ve.getNoteAssegnazione());
    }

    @Override
    public void delete(Long idVolo, Long idUtente) {
        String sql = "DELETE FROM volo_equipaggio WHERE id_volo = ? AND id_utente = ?";
        jdbcTemplate.update(sql, idVolo, idUtente);
    }
}