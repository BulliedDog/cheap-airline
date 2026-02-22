package com.skywings.repository.repositories;

import com.skywings.mapper.CittaMapper;
import com.skywings.model.Citta;
import com.skywings.repository.interfaces.CittaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CittaRepository implements CittaDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private CittaMapper cittaMapper;

    @Override
    public List<Citta> findAll() {
        String sql = "SELECT * FROM citta";
        return jdbcTemplate.query(sql, cittaMapper);
    }

    @Override
    public Optional<Citta> findById(Long id) {
        String sql = "SELECT * FROM citta WHERE id = ?";
        return jdbcTemplate.query(sql, cittaMapper, id)
                .stream()
                .findFirst();
    }

    @Override
    public void save(Citta citta) {
        String sql = "INSERT INTO citta (nome, nazione, codice_iata) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, citta.getNome(), citta.getNazione(), citta.getCodiceIata());
    }

    @Override
    public void update(Citta citta) {
        String sql = "UPDATE citta SET nome = ?, nazione = ?, codice_iata = ? WHERE id = ?";
        jdbcTemplate.update(sql, citta.getNome(), citta.getNazione(), citta.getCodiceIata(), citta.getId());
    }

    @Override
    public void deleteById(Long id) {
        String sql = "DELETE FROM citta WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}