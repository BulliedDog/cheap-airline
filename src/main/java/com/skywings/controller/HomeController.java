package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.service.*;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final VoloService voloService;
    private final CittaService cittaService;
    private final GestoreSessione gestoreSessione;
    private final PrenotazioneService prenotazioneService;
    private final AereoService aereoService;

    public HomeController(GestoreSessione gestoreSessione,
                          VoloService voloService,
                          CittaService cittaService,
                          UtenteService utenteService,
                          PrenotazioneService prenotazioneService,
                          AereoService aereoService) {
        this.voloService = voloService;
        this.cittaService = cittaService;
        this.gestoreSessione = gestoreSessione;
        this.prenotazioneService = prenotazioneService;
        this.aereoService = aereoService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        model.addAttribute("voli", voloService.getAllVoliConPrezzo());
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());
        model.addAttribute("cittaCasuali", cittaService.getCittaConVoliProgrammatiLimit4());
        model.addAttribute("aereiCasuali", aereoService.getAereiCasualiLimit2());

        Utente utente = gestoreSessione.getUtenteCorrente(session);
        if (utente != null) {
            model.addAttribute("miePrenotazioni", prenotazioneService.getPrenotazioniPerUtente(utente.getId()));
        }

        return "index";
    }
}