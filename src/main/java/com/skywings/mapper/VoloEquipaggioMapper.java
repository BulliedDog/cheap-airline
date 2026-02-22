package com.skywings.mapper;

import com.skywings.model.VoloEquipaggio;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VoloEquipaggioMapper implements RowMapper<VoloEquipaggio> {

    @Override
    public VoloEquipaggio mapRow(ResultSet rs, int rowNum) throws SQLException {
        VoloEquipaggio ve = new VoloEquipaggio();
        ve.setIdVolo(rs.getLong("id_volo"));
        ve.setIdUtente(rs.getLong("id_utente"));
        ve.setNoteAssegnazione(rs.getString("note_assegnazione"));
        return ve;
    }
}