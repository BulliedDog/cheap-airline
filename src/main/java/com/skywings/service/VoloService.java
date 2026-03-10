package com.skywings.service;

import com.skywings.dto.VoloDTO;
import com.skywings.mapper.VoloMapper;
import com.skywings.model.Volo;
import com.skywings.observer.VoloStatoEvent;
import com.skywings.repository.interfaces.VoloDAO;
import com.skywings.strategy.TariffaStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class VoloService {

    @Autowired
    private VoloDAO voloDAO;

    // --- NUOVE DIPENDENZE AGGIUNTE ---
    @Autowired
    private VoloMapper voloMapper;

    @Autowired
    @Qualifier("tariffaDinamica") //Strategia di default
    private TariffaStrategy tariffaStrategy;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Transactional
    public void aggiornaStatoVolo(Long idVolo, Volo.StatoVolo nuovoStato) {
        Volo v = voloDAO.findById(idVolo)
                .orElseThrow(() -> new RuntimeException("Volo non trovato con ID: " + idVolo));

        v.setStato(nuovoStato);
        voloDAO.save(v);

        eventPublisher.publishEvent(new VoloStatoEvent(idVolo, v.getCodiceVolo(), nuovoStato));
    }

    public List<Volo> getAllVoli() {
        return voloDAO.findAll();
    }

    public Volo getVoloById(Long id) {
        return voloDAO.findById(id).orElse(null);
    }

    public List<Volo> getVoliFiltered(Long originId, Long destId, LocalDate date) {
        return voloDAO.findVoliFiltered(originId, destId, date);
    }

    public void createVolo(Volo volo) {
        voloDAO.save(volo);
    }

    public void updateVolo(Volo volo) {
        voloDAO.update(volo);
    }

    public void deleteVolo(Long id) {
        voloDAO.deleteById(id);
    }


    // ==========================================
    // 2. NUOVI METODI PER UTENTI (Con DTO e Strategy)
    // Da chiamare nei Controller dedicati ai passeggeri
    // ==========================================

    public List<VoloDTO> getAllVoliConPrezzo() {
        // Riusiamo il metodo originale per prendere i dati dal DB
        List<Volo> voliDalDb = getAllVoli();
        List<VoloDTO> voliDaMostrare = new ArrayList<>();

        for (Volo volo : voliDalDb) {
            VoloDTO dto = voloMapper.toDto(volo);
            // Applichiamo la strategia per calcolare il prezzo finale
            dto.setPrezzoCalcolato(tariffaStrategy.calcolaPrezzo(volo, volo.getPrezzoBase()));
            voliDaMostrare.add(dto);
        }
        return voliDaMostrare;
    }

    public VoloDTO getVoloByIdConPrezzo(Long id) {
        Volo volo = getVoloById(id);
        if (volo == null) {
            return null;
        }
        VoloDTO dto = voloMapper.toDto(volo);
        dto.setPrezzoCalcolato(tariffaStrategy.calcolaPrezzo(volo, volo.getPrezzoBase()));
        return dto;
    }

    public List<VoloDTO> getVoliFilteredConPrezzo(Long originId, Long destId, LocalDate date) {
        // Riusiamo il metodo originale di filtraggio
        List<Volo> voliDalDb = getVoliFiltered(originId, destId, date);
        List<VoloDTO> voliDaMostrare = new ArrayList<>();

        for (Volo volo : voliDalDb) {
            VoloDTO dto = voloMapper.toDto(volo);
            dto.setPrezzoCalcolato(tariffaStrategy.calcolaPrezzo(volo, volo.getPrezzoBase()));
            voliDaMostrare.add(dto);
        }
        return voliDaMostrare;
    }
}