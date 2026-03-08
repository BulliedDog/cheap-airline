package com.skywings.mapper;

import com.skywings.model.VoloEquipaggio;
import com.skywings.dto.VoloEquipaggioDTO;
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

    public VoloEquipaggioDTO toDto(VoloEquipaggio entity) {
        if (entity == null) {
            return null;
        }

        VoloEquipaggioDTO dto = new VoloEquipaggioDTO();
        dto.setIdUtente(entity.getIdUtente());
        dto.setIdVolo(entity.getIdVolo());
        dto.setNoteAssegnazione(entity.getNoteAssegnazione());

        return dto;
    }

    public VoloEquipaggio toEntity(VoloEquipaggioDTO dto) {
        if (dto == null) {
            return null;
        }

        VoloEquipaggio entity = new VoloEquipaggio();
        entity.setIdUtente(dto.getIdUtente());
        entity.setIdVolo(dto.getIdVolo());
        entity.setNoteAssegnazione(dto.getNoteAssegnazione());

        return entity;
    }
}