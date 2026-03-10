package com.skywings.service;

import com.skywings.dto.PrenotazioneDTO;
import com.skywings.dto.VoloDTO;
import com.skywings.mapper.PrenotazioneMapper;
import com.skywings.model.Aereo;
import com.skywings.model.Prenotazione;
import com.skywings.repository.interfaces.PrenotazioneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PrenotazioneService {

    @Autowired
    private PrenotazioneDAO prenotazioneDAO;

    @Autowired
    private PrenotazioneMapper prenotazioneMapper;

    private final AereoService aereoService;
    private final VoloService voloService;

    public PrenotazioneService(PrenotazioneDAO prenotazioneDAO,
                               PrenotazioneMapper prenotazioneMapper,
                               VoloService voloService,
                               AereoService aereoService){
        this.prenotazioneDAO = prenotazioneDAO;
        this.prenotazioneMapper = prenotazioneMapper;
        this.voloService = voloService;
        this.aereoService = aereoService;
    }

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

    @Transactional
    public void creaPrenotazione(Long idUtente, Long idVolo, String nome, String cognome, String numeroDocumento, String classe, String posto) {

        // 1. Recuperiamo il VoloDTO (che HA GIÀ applicato la TariffaStrategy)
        VoloDTO volo = voloService.getVoloByIdConPrezzo(idVolo);
        Aereo aereo = aereoService.getAereoById(volo.getIdAereo());

        // 2. Controllo capacità dell'aereo
        int capacitaMassima = classe.equalsIgnoreCase("Business")
                ? aereo.getCapacitaBusiness()
                : aereo.getCapacitaEconomy();

        int postiOccupati = prenotazioneDAO.countPrenotazioniByVoloAndClasse(idVolo, classe);

        if (postiOccupati >= capacitaMassima) {
            throw new IllegalStateException("We are sorry, " + classe + " class is fully booked for this flight.");
        }

        // 3. Creiamo l'oggetto Prenotazione
        Prenotazione p = new Prenotazione();
        p.setUtenteId(idUtente);
        p.setVoloId(idVolo);
        p.setNomePasseggero(nome);
        p.setCognomePasseggero(cognome);
        p.setNumeroDocumento(numeroDocumento);
        p.setClasse(classe);
        p.setPosto(posto);
        p.setDataPrenotazione(LocalDateTime.now());

        p.setPrezzoAcquistato(volo.getPrezzoCalcolato());
        prenotazioneDAO.save(p);
    }
}