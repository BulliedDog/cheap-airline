package com.skywings.service;

import com.skywings.mapper.VoloEquipaggioMapper;
import com.skywings.model.VoloEquipaggio;
import com.skywings.dto.VoloEquipaggioDTO;
import com.skywings.repository.interfaces.VoloEquipaggioDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoloEquipaggioService {

    private final VoloEquipaggioDAO voloEquipaggioDAO;
    private final VoloEquipaggioMapper voloEquipaggioMapper;

    public VoloEquipaggioService(VoloEquipaggioDAO voloEquipaggioDAO, VoloEquipaggioMapper voloEquipaggioMapper) {
        this.voloEquipaggioDAO = voloEquipaggioDAO;
        this.voloEquipaggioMapper = voloEquipaggioMapper;
    }

    public List<VoloEquipaggio> getAllEquipaggio() {
        return voloEquipaggioDAO.findAll();
    }

    public List<VoloEquipaggioDTO> getAllDettagli() {
        // Assicurati che il metodo findAllDettagli() sia presente nel tuo VoloEquipaggioRepository
        return voloEquipaggioDAO.findAllDettagli();
    }

    public void addMembro(VoloEquipaggio eq) {
        VoloEquipaggio ve = new VoloEquipaggio();
        ve.setIdVolo(eq.getIdVolo());
        ve.setIdUtente(eq.getIdUtente());
        ve.setNoteAssegnazione(eq.getNoteAssegnazione());

        voloEquipaggioDAO.save(ve);
    }

    public void rimuoviMembro(Long idVolo, Long idUtente) {
        voloEquipaggioDAO.delete(idVolo, idUtente);
    }

    public VoloEquipaggioDTO getAssegnazioneByIds(Long idVolo, Long idUtente) {
        VoloEquipaggio entity = voloEquipaggioDAO.findByIds(idVolo, idUtente);
        // Trasformala in DTO per la vista
        return voloEquipaggioMapper.toDto(entity);
    }
}