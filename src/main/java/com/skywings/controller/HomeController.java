package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.service.VoloService; // Import aggiunto
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final GestoreSessione gestoreSessione;
    private final VoloService voloService;

    public HomeController(GestoreSessione gestoreSessione, VoloService voloService) {
        this.gestoreSessione = gestoreSessione;
        this.voloService = voloService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {

        Utente utenteLoggato = gestoreSessione.getUtenteCorrente(session);
        model.addAttribute("utente", utenteLoggato);
        model.addAttribute("voli", voloService.getAllVoli());

        return "index";
    }
}