package com.skywings.controller;

import com.skywings.model.Volo;
import com.skywings.model.Aereo;
import com.skywings.model.Utente; // Aggiunto l'import per l'Utente
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@Controller
@RequestMapping("/admin") // Tutte le rotte inizieranno con /admin
public class AdminController {

    private final GestoreSessione gestoreSessione;

    // In futuro inietterai qui i tuoi Service/DAO
    // private final VoloService voloService;

    public AdminController(GestoreSessione gestoreSessione) {
        this.gestoreSessione = gestoreSessione;
    }

    // Metodo helper per controllare se l'utente è loggato ED È UN ADMIN
    private boolean isAdminLoggato(HttpSession session) {
        Utente utente = gestoreSessione.getUtenteCorrente(session);
        // Controlla che esista e che il suo ruolo sia esattamente ADMIN
        return utente != null && "ADMIN".equalsIgnoreCase(utente.getRuolo());
    }

    // --- DASHBOARD ---
    @GetMapping
    public String dashboard(HttpSession session) {
        if (!isAdminLoggato(session)) return "redirect:/login";
        return "admin-dashboard";
    }

    // ==========================================
    //               CRUD VOLI
    // ==========================================

    // 1. LISTA VOLI (Read)
    @GetMapping("/voli")
    public String listaVoli(HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        // Finto recupero dati: model.addAttribute("voli", voloService.trovaTutti());
        model.addAttribute("voli", new ArrayList<Volo>());
        return "admin-voli"; // Mostra la pagina della tabella
    }

    // 2. FORM NUOVO VOLO (Create - Vista)
    @GetMapping("/voli/nuovo")
    public String nuovoVolo(HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        model.addAttribute("volo", new Volo());
        // model.addAttribute("aerei", aereoService.trovaTutti()); // Per la tendina degli aerei
        return "admin-volo-form";
    }

    // 3. SALVATAGGIO VOLO (Create/Update - Azione)
    @PostMapping("/voli")
    public String salvaVolo(@ModelAttribute Volo volo, HttpSession session) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        // voloService.salva(volo);
        return "redirect:/admin/voli"; // Torna alla lista dopo il salvataggio
    }

    // 4. FORM MODIFICA VOLO (Update - Vista)
    @GetMapping("/voli/modifica/{id}")
    public String modificaVolo(@PathVariable Long id, HttpSession session, Model model) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        // Volo volo = voloService.trovaPerId(id);
        model.addAttribute("volo", new Volo()); // Oggetto finto per ora
        return "admin-volo-form";
    }

    // 5. ELIMINAZIONE VOLO (Delete - Azione)
    @PostMapping("/voli/elimina/{id}")
    public String eliminaVolo(@PathVariable Long id, HttpSession session) {
        if (!isAdminLoggato(session)) return "redirect:/login";

        // voloService.elimina(id);
        return "redirect:/admin/voli";
    }
}