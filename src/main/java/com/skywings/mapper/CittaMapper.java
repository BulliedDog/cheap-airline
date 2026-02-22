package com.skywings.mapper;

import com.skywings.model.Citta;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class CittaMapper implements RowMapper<Citta> {

    @Override
    public Citta mapRow(ResultSet rs, int rowNum) throws SQLException {
        Citta citta = new Citta();
        citta.setId(rs.getLong("id"));
        citta.setNome(rs.getString("nome"));
        citta.setNazione(rs.getString("nazione"));
        citta.setCodiceIata(rs.getString("codice_iata")); // Mappatura snake_case -> camelCase
        return citta;
    }
}