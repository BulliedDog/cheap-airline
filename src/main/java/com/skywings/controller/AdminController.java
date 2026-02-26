package com.skywings.controller;

import com.skywings.model.Aereo;
import com.skywings.model.Citta;
import com.skywings.model.Volo;
import com.skywings.model.Utente;
import com.skywings.repository.interfaces.UtenteDAO;
import com.skywings.service.*;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final GestoreSessione gestoreSessione;
    private final VoloService voloService;
    private final AereoService aereoService;
    private final CittaService cittaService;
    private final UtenteService utenteService;
    private final VoloEquipaggioService equipaggioService;

    public AdminController(GestoreSessione gestoreSessione,
                           VoloService voloService,
                           AereoService aereoService,
                           CittaService cittaService,
                           UtenteService utenteService,
                           VoloEquipaggioService equipaggioService) {
        this.gestoreSessione = gestoreSessione;
        this.voloService = voloService;
        this.aereoService = aereoService;
        this.cittaService = cittaService;
        this.utenteService = utenteService;
        this.equipaggioService = equipaggioService;
    }

    private boolean isAdminLoggato(HttpSession session) {
        Utente utente = gestoreSessione.getUtenteCorrente(session);
        return utente != null && "ADMIN".equalsIgnoreCase(utente.getRuolo());
    }

    @GetMapping("")
    public String dashboardPrincipale(HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        model.addAttribute("countVoli", voloService.getAllVoli().size());
        model.addAttribute("countAerei", aereoService.getAllAerei().size());
        model.addAttribute("countCitta", cittaService.getAllCitta().size());
        model.addAttribute("countUtenti", utenteService.getAllUtenti().size());
        model.addAttribute("countEquipaggio", equipaggioService.getAllLegami().size());

        return "admin-dashboard";
    }

    @PostMapping("/equipaggio/elimina/{compositeId}")
    public String eliminaAssegnazione(@PathVariable String compositeId, HttpSession session) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        try {
            String[] ids = compositeId.split("-");
            Long idVolo = Long.parseLong(ids[0]);
            Long idUtente = Long.parseLong(ids[1]);

            // Ora usiamo l'istanza iniettata correttamente, non un "new" fatto a caso
            equipaggioService.rimuoviMembro(idVolo, idUtente);
        } catch (Exception e) {
            // Log dell'errore se il formato dell'ID è sbagliato
        }

        return "redirect:/admin/equipaggio";
    }
}