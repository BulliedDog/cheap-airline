package com.skywings.controller;

import com.skywings.model.Volo;
import com.skywings.model.Utente;
import com.skywings.service.VoloService;
import com.skywings.service.AereoService;
import com.skywings.service.CittaService;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final GestoreSessione gestoreSessione;
    private final VoloService voloService;
    private final AereoService aereoService;
    private final CittaService cittaService;

    // Costruttore con iniezione di tutti i servizi necessari
    public AdminController(GestoreSessione gestoreSessione,
                           VoloService voloService,
                           AereoService aereoService,
                           CittaService cittaService) {
        this.gestoreSessione = gestoreSessione;
        this.voloService = voloService;
        this.aereoService = aereoService;
        this.cittaService = cittaService;
    }

    private boolean isAdminLoggato(HttpSession session) {
        Utente utente = gestoreSessione.getUtenteCorrente(session);
        return utente != null && "ADMIN".equalsIgnoreCase(utente.getRuolo());
    }

    // --- LISTA VOLI ---
    @GetMapping("/voli")
    public String listaVoli(HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        // Recupero reale dal database tramite il Service
        model.addAttribute("voli", voloService.getAllVoli());
        return "admin-voli";
    }

    // --- FORM NUOVO VOLO ---
    @GetMapping("/voli/nuovo")
    public String nuovoVolo(HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        model.addAttribute("volo", new Volo());
        // Passiamo alla vista le liste per popolare le select (tendine) nel form
        model.addAttribute("aerei", aereoService.getAllAerei());
        model.addAttribute("citta", cittaService.getAllCitta());
        model.addAttribute("statiVolo", Volo.StatoVolo.values());

        return "admin-volo-form";
    }

    // --- SALVATAGGIO (CREATE/UPDATE) ---
    @PostMapping("/voli")
    public String salvaVolo(@ModelAttribute Volo volo, HttpSession session) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        if (volo.getId() == null) {
            voloService.createVolo(volo);
        } else {
            voloService.updateVolo(volo);
        }
        return "redirect:/admin/voli";
    }

    // --- FORM MODIFICA ---
    @GetMapping("/voli/modifica/{id}")
    public String modificaVolo(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        Volo volo = voloService.getVoloById(id);
        if (volo == null) return "redirect:/admin/voli?error=notfound";

        model.addAttribute("volo", volo);
        model.addAttribute("aerei", aereoService.getAllAerei());
        model.addAttribute("citta", cittaService.getAllCitta());
        model.addAttribute("statiVolo", Volo.StatoVolo.values());

        return "admin-volo-form";
    }

    // --- ELIMINAZIONE ---
    @PostMapping("/voli/elimina/{id}")
    public String eliminaVolo(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        voloService.deleteVolo(id);
        return "redirect:/admin/voli";
    }
}