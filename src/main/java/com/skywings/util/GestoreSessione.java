package com.skywings.util;

import com.skywings.model.Utente;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

@Component
public class GestoreSessione {

    private static final String CHIAVE_SESSIONE = "UTENTE_LOGGATO";

    // Mette l'utente nella "scatola" della sessione
    public void salvaUtente(HttpSession session, Utente utente) {
        session.setAttribute(CHIAVE_SESSIONE, utente);
    }

    // Tira fuori l'utente dalla "scatola" (o null se non c'è)
    public Utente getUtenteCorrente(HttpSession session) {
        return (Utente) session.getAttribute(CHIAVE_SESSIONE);
    }

    // Svuota la "scatola" (Logout)
    public void cancellaSessione(HttpSession session) {
        session.invalidate();
    }
}