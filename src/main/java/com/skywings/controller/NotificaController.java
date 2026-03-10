package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.repository.interfaces.NotificaDAO;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NotificaController {

    private final NotificaDAO notificaDAO;
    private final GestoreSessione gestoreSessione;

    public NotificaController(NotificaDAO notificaDAO, GestoreSessione gestoreSessione) {
        this.notificaDAO = notificaDAO;
        this.gestoreSessione = gestoreSessione;
    }

    @PostMapping("/notifiche/leggi-tutte")
    public String leggiTutte(HttpSession session, HttpServletRequest request) {
        Utente utente = gestoreSessione.getUtenteCorrente(session);
        if (utente != null) {
            notificaDAO.segnaTutteComeLette(utente.getId());
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }
}