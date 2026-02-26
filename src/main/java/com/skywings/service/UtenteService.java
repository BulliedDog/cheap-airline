package com.skywings.service;

import com.skywings.model.Utente;
import com.skywings.repository.interfaces.UtenteDAO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtenteService {

    private final UtenteDAO utenteDAO;

    public UtenteService(UtenteDAO utenteDAO) {
        this.utenteDAO = utenteDAO;
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
            utente.setRuolo("USER");
        }
        utenteDAO.save(utente);
    }

    public void updateUtente(Utente utente) {
        utenteDAO.update(utente);
    }

    public void deleteUtente(Long id) {
        utenteDAO.deleteById(id);
    }
}