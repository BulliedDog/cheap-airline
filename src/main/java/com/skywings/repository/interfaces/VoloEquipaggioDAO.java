package com.skywings.repository.interfaces;

import com.skywings.model.VoloEquipaggio;
import com.skywings.dto.VoloEquipaggioDTO;

import java.util.List;

public interface VoloEquipaggioDAO {
    List<VoloEquipaggio> findAll();
    List<VoloEquipaggioDTO> findAllDettagli(); // Aggiungi questa riga se manca
    List<VoloEquipaggio> findByVoloId(Long idVolo);
    List<VoloEquipaggio> findByUtenteId(Long idUtente);
    void save(VoloEquipaggio voloEquipaggio);
    void delete(Long idVolo, Long idUtente);
}