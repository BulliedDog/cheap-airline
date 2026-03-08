package com.skywings.service;

import com.skywings.dto.PrenotazioneDTO;
import com.skywings.mapper.PrenotazioneMapper;
import com.skywings.model.Prenotazione;
import com.skywings.repository.interfaces.PrenotazioneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneDAO prenotazioneDAO;

    @Autowired
    private PrenotazioneMapper prenotazioneMapper;

    public void addPrenotazione(Prenotazione pre) {
        if (pre.getDataPrenotazione() == null) {
            pre.setDataPrenotazione(LocalDateTime.now());
        }
        prenotazioneDAO.save(pre);
    }

    public void creaPrenotazione(PrenotazioneDTO dto) {
        if (dto.getDataPrenotazione() == null) {
            dto.setDataPrenotazione(LocalDateTime.now());
        }
        Prenotazione entity = prenotazioneMapper.toEntity(dto);
        prenotazioneDAO.save(entity);
    }

    public List<PrenotazioneDTO> getPrenotazioniPerUtente(Long utenteId) {
        return prenotazioneDAO.findByUtenteId(utenteId).stream()
                .map(prenotazioneMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PrenotazioneDTO> getPrenotazioniPerVolo(Long voloId) {
        return prenotazioneDAO.findByVoloId(voloId).stream()
                .map(prenotazioneMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PrenotazioneDTO> getAllPrenotazioni() {
        return prenotazioneDAO.findAll().stream()
                .map(prenotazioneMapper::toDto)
                .collect(Collectors.toList());
    }

    public PrenotazioneDTO getPrenotazioneById(Long id) {
        Prenotazione entity = prenotazioneDAO.findById(id);
        return prenotazioneMapper.toDto(entity);
    }

    public void deletePrenotazioneById(Long id) {
        prenotazioneDAO.deleteById(id);
    }
}