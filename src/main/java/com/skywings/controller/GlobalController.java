package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.repository.interfaces.NotificaDAO;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Map;

@ControllerAdvice
public class GlobalController {

    private GestoreSessione gestoreSessione;
    private NotificaDAO notificaDAO;

    public GlobalController(NotificaDAO notificaDAO, GestoreSessione gestoreSessione) {
        this.notificaDAO = notificaDAO;
        this.gestoreSessione = gestoreSessione;
    }

    @ModelAttribute("utente")
    public Utente aggiungiUtenteAlModello(HttpSession session) {
        return gestoreSessione.getUtenteCorrente(session);
    }

    @ModelAttribute
    public void addAttributes(Model model, HttpSession session) {
        Utente utente = gestoreSessione.getUtenteCorrente(session);
        if (utente != null) {
            List<Map<String, Object>> notifiche = notificaDAO.getNotificheNonLette(utente.getId());
            model.addAttribute("notifiche", notifiche);
            model.addAttribute("notificheCount", notifiche.size());
        }
    }
}