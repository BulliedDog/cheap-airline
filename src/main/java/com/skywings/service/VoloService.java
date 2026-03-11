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

    public List<Volo> getAllVoli() {
        return voloDAO.findAll();
    }

    public Volo getVoloById(Long id) {
        return voloDAO.findById(id).orElse(null);
    }

    public List<Volo> getVoliFiltered(Long originId, Long destId, LocalDate date) {
        return voloDAO.findVoliFiltered(originId, destId, date);
    }

    @Transactional
    public void saveVolo(Volo voloAggiornato) {
        // 1. Verifichiamo se il volo esiste già (è un update)
        if (voloAggiornato.getId() != null) {
            Volo voloAttuale = voloDAO.findById(voloAggiornato.getId())
                    .orElseThrow(() -> new RuntimeException("Volo non trovato"));

            // 2. Controllo se lo stato è cambiato
            boolean statoCambiato = !voloAttuale.getStato().equals(voloAggiornato.getStato());

            // 3. Eseguo l'update/save
            voloDAO.save(voloAggiornato);

            // 4. Pubblico l'evento solo se lo stato è effettivamente cambiato
            if (statoCambiato) {
                eventPublisher.publishEvent(new VoloStatoEvent(
                        voloAggiornato.getId(),
                        voloAggiornato.getCodiceVolo(),
                        voloAggiornato.getStato()
                ));
                System.out.println("DEBUG: Stato cambiato in " + voloAggiornato.getStato() + ". Evento pubblicato.");
            }
        } else {
            // Se l'ID è null, è un nuovo inserimento (Create)
            voloDAO.save(voloAggiornato);
            System.out.println("DEBUG: Nuovo volo creato, nessun evento di cambio stato necessario.");
        }
    }

    @Transactional
    public void aggiornaStatoVolo(Long idVolo, Volo.StatoVolo nuovoStato) {
        Volo v = voloDAO.findById(idVolo)
                .orElseThrow(() -> new RuntimeException("Volo non trovato con ID: " + idVolo));

        // Controllo se è davvero una variazione
        if (!v.getStato().equals(nuovoStato)) {
            System.out.println("DEBUG: Pubblicazione evento per volo " + idVolo + "aggiornaStatoVolo");
            v.setStato(nuovoStato);
            voloDAO.save(v);
            eventPublisher.publishEvent(new VoloStatoEvent(idVolo, v.getCodiceVolo(), nuovoStato));
        }
    }

    public void deleteVolo(Long id) {
        voloDAO.deleteById(id);
    }

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