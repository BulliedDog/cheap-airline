package com.skywings.repository.repositories;

import com.skywings.mapper.AereoMapper;
import com.skywings.model.Aereo;
import com.skywings.repository.interfaces.AereoDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class AereoRepository implements AereoDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private AereoMapper aereoMapper;

    @Override
    public List<Aereo> findAll() {
        // Deve essere "aerei" al plurale
        String sql = "SELECT * FROM aerei";
        return jdbcTemplate.query(sql, aereoMapper);
    }

    @Override
    public Optional<Aereo> findById(Long id) {
        String sql = "SELECT * FROM aereo WHERE id = ?";
        return jdbcTemplate.query(sql, aereoMapper, id).stream().findFirst();
    }

    @Override
    public void save(Aereo aereo) {
        // Verifica che i nomi colonne corrispondano al tuo schema:
        // modello, produttore, capacita_economy, capacita_business
        String sql = "INSERT INTO aerei (modello, produttore, capacita_economy, capacita_business) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql, aereo.getModello(), aereo.getProduttore(), aereo.getCapacitaEconomy(), aereo.getCapacitaBusiness());
    }

    @Override
    public void update(Aereo aereo) {
        String sql = "UPDATE aereo SET modello = ?, produttore = ?, capacita_economy = ?, capacita_business = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                aereo.getModello(),
                aereo.getProduttore(),
                aereo.getCapacitaEconomy(),
                aereo.getCapacitaBusiness(),
                aereo.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM aereo WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}