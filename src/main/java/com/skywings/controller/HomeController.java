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

@Controller
public class HomeController {

    private final GestoreSessione gestoreSessione;
    private final VoloService voloService;
    private final CittaService cittaService;

    public HomeController(GestoreSessione gestoreSessione, VoloService voloService, CittaService cittaService, UtenteService utenteService) {
        this.gestoreSessione = gestoreSessione;
        this.voloService = voloService;
        this.cittaService = cittaService;
    }

    @GetMapping("/")
    public String index(HttpSession session, Model model) {
        model.addAttribute("voli", voloService.getAllVoliConPrezzo());
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());
        return "index";
    }
}