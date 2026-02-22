package com.skywings.repository.interfaces;

import com.skywings.model.Aereo;
import java.util.List;
import java.util.Optional;

public interface AereoDAO {
    List<Aereo> findAll();
    Optional<Aereo> findById(Long id);
    void save(Aereo aereo);
    void update(Aereo aereo);
    void deleteById(Long id);
}