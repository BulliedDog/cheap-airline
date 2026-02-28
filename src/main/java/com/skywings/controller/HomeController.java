package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.service.CittaService;
import com.skywings.service.UtenteService;
import com.skywings.service.VoloService;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
public class HomeController {

    private final GestoreSessione gestoreSessione;
    private final VoloService voloService;
    private final CittaService cittaService;
    private final UtenteService utenteService;

    public HomeController(GestoreSessione gestoreSessione, VoloService voloService, CittaService cittaService, UtenteService utenteService) {
        this.gestoreSessione = gestoreSessione;
        this.voloService = voloService;
        this.cittaService = cittaService;
        this.utenteService = utenteService;
    }

    @ModelAttribute
    public void addAttributes(HttpSession session, Model model) {
        Utente utente = gestoreSessione.getUtenteCorrente(session);
        if (utente != null) {
            model.addAttribute("utente", utente);
            System.out.println("Utente in sessione: " + utente.getNome() + " Ruolo: " + utente.getRuolo());
        }
    }

    @GetMapping("/")
    public String index(Model model) {
        // L'utente viene già aggiunto dal metodo sopra
        model.addAttribute("voli", voloService.getAllVoli());
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());
        return "index";
    }
}