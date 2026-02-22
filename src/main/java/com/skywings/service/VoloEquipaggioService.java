package com.skywings.service;

import com.skywings.model.VoloEquipaggio;
import com.skywings.repository.interfaces.VoloEquipaggioDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoloEquipaggioService {

    @Autowired
    private VoloEquipaggioDAO voloEquipaggioDAO;

    public List<VoloEquipaggio> getEquipaggioPerVolo(Long idVolo) {
        return voloEquipaggioDAO.findByVoloId(idVolo);
    }

    public void assegnaMembro(VoloEquipaggio ve) {
        voloEquipaggioDAO.save(ve);
    }

    public void rimuoviMembro(Long idVolo, Long idUtente) {
        voloEquipaggioDAO.delete(idVolo, idUtente);
    }
}