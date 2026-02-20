package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final GestoreSessione gestoreSessione;

    public HomeController(GestoreSessione gestoreSessione) {
        this.gestoreSessione = gestoreSessione;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {

        Utente utenteLoggato = gestoreSessione.getUtenteCorrente(session);
        model.addAttribute("utente", utenteLoggato);

        return "index";
    }
}