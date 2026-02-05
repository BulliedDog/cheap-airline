package com.skywings.mapper;

import com.skywings.model.Utente;
import org.springframework.jdbc.core.RowMapper;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UtenteMapper implements RowMapper<Utente> {

    @Override
    public Utente mapRow(ResultSet rs, int rowNum) throws SQLException {
        Utente utente = new Utente();

        utente.setId(rs.getLong("id"));
        utente.setUsername(rs.getString("username"));
        utente.setEmail(rs.getString("email"));
        utente.setPassword(rs.getString("password"));
        utente.setNome(rs.getString("nome"));
        utente.setCognome(rs.getString("cognome"));
        utente.setRuolo(rs.getString("ruolo"));

        return utente;
    }
}