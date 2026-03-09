package com.skywings.service;

import com.skywings.model.Utente;
import com.skywings.repository.interfaces.UtenteDAO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UtenteService {

    private final UtenteDAO utenteDAO;
    private final AutenticazioneService autenticazioneService;

    public UtenteService(UtenteDAO utenteDAO, AutenticazioneService autenticazioneService) {
        this.utenteDAO = utenteDAO;
        this.autenticazioneService = autenticazioneService;
    }

    public List<Utente> getAllUtenti() {
        return utenteDAO.findAll();
    }

    public Utente getUtenteById(Long id) {
        return utenteDAO.findById(id).orElse(null);
    }

    public void addUtente(Utente utente) {
        // Se non specificato, impostiamo un ruolo di default
        if (utente.getRuolo() == null || utente.getRuolo().isEmpty()) {
            utente.setRuolo("CLIENTE");
        }
        utente.setPassword(autenticazioneService.criptaPassword(utente.getPassword()));
        utenteDAO.save(utente);
    }

    public void updateUtente(Utente utente) {
        utente.setPassword(autenticazioneService.criptaPassword(utente.getPassword()));
        utenteDAO.update(utente);
    }

    public void deleteUtente(Long id) {
        utenteDAO.deleteById(id);
    }

    public List<Utente> getAllStaff() {
        return utenteDAO.findAll().stream()
                .filter(u -> !"USER".equals(u.getRuolo()))
                .collect(Collectors.toList());
    }

    public List<Utente> getMembriEquipaggio() {
        return utenteDAO.findAll().stream()
                .filter(u -> "PILOTA".equals(u.getRuolo()) || "HOSTESS".equals(u.getRuolo()))
                .collect(Collectors.toList());
    }
}