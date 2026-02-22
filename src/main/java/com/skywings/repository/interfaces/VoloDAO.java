package com.skywings.repository.interfaces;

import com.skywings.model.Volo;
import java.util.List;
import java.util.Optional;

public interface VoloDAO {
    List<Volo> findAll();
    Optional<Volo> findById(Long id);
    void save(Volo volo);
    void update(Volo volo);
    void deleteById(Long id);
}