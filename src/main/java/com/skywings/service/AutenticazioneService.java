package com.skywings.service;

import com.skywings.model.Utente;
import com.skywings.repository.interfaces.UtenteDAO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AutenticazioneService {

    private final UtenteDAO utenteDAO;
    // L'encoder per gestire l'hash in modo sicuro
    private final BCryptPasswordEncoder passwordEncoder;

    public AutenticazioneService(UtenteDAO utenteRepository) {
        this.utenteDAO = utenteRepository; // CORRETTO
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    /**
     * Metodo per il LOGIN:
     * Confronta lo username e verifica se l'hash della password combacia.
     */
    public Utente login(String username, String passwordInserita) {
        // Cerchiamo per username come da tabella
        Optional<Utente> utenteDb = utenteDAO.findByUsername(username);

        if (utenteDb.isPresent()) {
            Utente utente = utenteDb.get();
            if (passwordEncoder.matches(passwordInserita, utente.getPassword())) {
                return utente;
            }
        }
        return null;
    }

    /**
     * Metodo per la REGISTRAZIONE:
     * Prende l'utente, cripta la sua password e lo salva.
     */
    public Utente registra(Utente utente) {
        // 1. Prendiamo la password scritta dall'utente nel form
        String passwordInChiaro = utente.getPassword();

        // 2. Creiamo l'hash sicuro (Esempio: "mio123" -> "$2a$10$7R...")
        String passwordHashata = passwordEncoder.encode(passwordInChiaro);

        // 3. Settiamo la password criptata nell'oggetto
        utente.setPassword(passwordHashata);

        // 4. Salviamo l'utente nel database
        return utenteDAO.save(utente);
    }
}