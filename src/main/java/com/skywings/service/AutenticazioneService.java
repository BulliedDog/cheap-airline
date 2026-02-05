package com.skywings.service;

import com.skywings.model.Utente;
import com.skywings.repository.UtenteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticazioneService {

    private final UtenteRepository utenteRepository;

    // Spring inietta (collega) automaticamente il Repository qui dentro
    public AutenticazioneService(UtenteRepository utenteRepository) {
        this.utenteRepository = utenteRepository;
    }

    /**
     * Questo metodo riceve username e password dal Controller.
     * 1. Chiede al Database se l'utente esiste.
     * 2. Se esiste, controlla se la password combacia.
     */
    public Utente login(String username, String passwordInserita) {

        // 1. Cerchiamo l'utente nel DB
        Optional<Utente> utenteDb = utenteRepository.trovaPerUsername(username);

        // 2. Se l'utente c'è...
        if (utenteDb.isPresent()) {
            Utente utente = utenteDb.get();

            // ...controlliamo la password (attenzione: in futuro useremo password criptate!)
            if (utente.getPassword().equals(passwordInserita)) {
                return utente; // Login OK! Restituisco l'utente completo
            }
        }

        return null; // Utente non trovato o password sbagliata
    }


}