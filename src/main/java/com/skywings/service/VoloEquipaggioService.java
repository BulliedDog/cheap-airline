package com.skywings.service;

import com.skywings.model.VoloEquipaggio;
import com.skywings.model.VoloEquipaggioDTO;
import com.skywings.repository.interfaces.VoloEquipaggioDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoloEquipaggioService {

    private final VoloEquipaggioDAO voloEquipaggioDAO;

    public VoloEquipaggioService(VoloEquipaggioDAO voloEquipaggioDAO) {
        this.voloEquipaggioDAO = voloEquipaggioDAO;
    }

    // Questo è quello che mancava per il contatore della dashboard!
    public List<VoloEquipaggio> getAllLegami() {
        return voloEquipaggioDAO.findAll();
    }

    // Questo serve per la tabella "bella" con i nomi dei piloti e i codici volo
    public List<VoloEquipaggioDTO> getAllDettagli() {
        // Assicurati che il metodo findAllDettagli() sia presente nel tuo VoloEquipaggioRepository
        return voloEquipaggioDAO.findAllDettagli();
    }

    public void assegnaMembro(Long idVolo, Long idUtente) {
        VoloEquipaggio ve = new VoloEquipaggio();
        ve.setIdVolo(idVolo);
        ve.setIdUtente(idUtente);
        voloEquipaggioDAO.save(ve);
    }

    public void rimuoviMembro(Long idVolo, Long idUtente) {
        voloEquipaggioDAO.delete(idVolo, idUtente);
    }
}