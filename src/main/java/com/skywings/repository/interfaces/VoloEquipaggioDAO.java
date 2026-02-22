package com.skywings.repository.interfaces;

import com.skywings.model.VoloEquipaggio;
import java.util.List;

public interface VoloEquipaggioDAO {
    List<VoloEquipaggio> findAll();
    List<VoloEquipaggio> findByVoloId(Long idVolo);
    List<VoloEquipaggio> findByUtenteId(Long idUtente);
    void save(VoloEquipaggio voloEquipaggio);
    void delete(Long idVolo, Long idUtente);
}