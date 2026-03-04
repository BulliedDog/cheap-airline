package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalController {

    @Autowired
    private GestoreSessione gestoreSessione;

    @ModelAttribute("utente") // Questo rende ${utente} disponibile ovunque
    public Utente aggiungiUtenteAlModello(HttpSession session) {
        return gestoreSessione.getUtenteCorrente(session);
    }
}