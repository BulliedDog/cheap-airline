package com.skywings.mapper;

import com.skywings.model.Aereo;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class AereoMapper implements RowMapper<Aereo> {
    @Override
    public Aereo mapRow(ResultSet rs, int rowNum) throws SQLException {
        Aereo a = new Aereo();
        a.setId(rs.getLong("id"));
        a.setModello(rs.getString("modello"));
        a.setProduttore(rs.getString("produttore"));
        a.setCapacitaEconomy(rs.getInt("capacita_economy"));
        a.setCapacitaBusiness(rs.getInt("capacita_business"));
        return a;
    }
}