package com.skywings.repository.interfaces;

import com.skywings.model.Citta;
import java.util.List;
import java.util.Optional;

public interface CittaDAO {
    List<Citta> findAll();
    Optional<Citta> findById(Long id);
    void save(Citta citta);
    void update(Citta citta);
    void deleteById(Long id);
}