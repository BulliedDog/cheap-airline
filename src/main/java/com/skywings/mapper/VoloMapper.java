package com.skywings.mapper;

import com.skywings.model.Volo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class VoloMapper implements RowMapper<Volo> {

    @Override
    public Volo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Volo volo = new Volo();
        volo.setId(rs.getLong("id"));
        volo.setCodiceVolo(rs.getString("codice_volo"));
        volo.setIdCittaPartenza(rs.getLong("id_citta_partenza"));
        volo.setIdCittaArrivo(rs.getLong("id_citta_arrivo"));
        volo.setIdAereo(rs.getLong("id_aereo"));
        volo.setOrarioPartenza(rs.getTimestamp("orario_partenza").toLocalDateTime());
        volo.setOrarioArrivo(rs.getTimestamp("orario_arrivo").toLocalDateTime());
        // PrezzoBase è BigDecimal nel modello e DECIMAL nel DB
        volo.setPrezzoBase(rs.getBigDecimal("prezzo_base"));
        // Lo stato è un Enum nel modello e VARCHAR nel DB
        volo.setStato(Volo.StatoVolo.valueOf(rs.getString("stato")));
        return volo;
    }
}