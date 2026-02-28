package com.skywings.controller;

import com.skywings.model.*;
import com.skywings.service.*;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private VoloService voloService;
    @Autowired private UtenteService utenteService;
    @Autowired private AereoService aereoService;
    @Autowired private CittaService cittaService;
    @Autowired private VoloEquipaggioService equipaggioService;
    @Autowired private GestoreSessione gestoreSessione;

    // DASHBOARD: Rotta /admin
    @GetMapping({"", "/"})
    public String dashboard(Model model, HttpSession session) {
        Utente admin = gestoreSessione.getUtenteCorrente(session);
        if (admin == null || !"ADMIN".equals(admin.getRuolo())) return "redirect:/login";

        // Contatori per le card della dashboard
        model.addAttribute("countVoli", voloService.getAllVoli().size());
        model.addAttribute("countAerei", aereoService.getAllAerei().size());
        model.addAttribute("countUtenti", utenteService.getAllUtenti().size());
        model.addAttribute("countCitta", cittaService.getAllCitta().size());
        model.addAttribute("countEquipaggio", utenteService.getAllStaff().size());

        // CORRETTO: Riferimento diretto al file in templates/
        return "admin-dashboard";
    }

    @GetMapping("/{tipo}/{azione}")
    public String mostraForm(@PathVariable String tipo, Model model, HttpSession session) {
        Utente admin = gestoreSessione.getUtenteCorrente(session);
        if (admin == null || !"ADMIN".equals(admin.getRuolo())) return "redirect:/login";

        model.addAttribute("tipo", tipo);
        model.addAttribute("utente", admin);

        // Inizializziamo le liste per evitare il crash di Thymeleaf se il tipo non le richiede
        switch (tipo) {
            case "voli":
                model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());
                model.addAttribute("aerei", aereoService.getAllAerei());
                break;
            case "equipaggio":
                model.addAttribute("tuttiVoli", voloService.getAllVoli());
                model.addAttribute("tuttiUtenti", utenteService.getAllStaff());
                break;
            default:
                // Per utenti, citta, aerei non servono liste esterne
                break;
        }

        return "universal-form";
    }

    // POST: Salvataggio (Invariato, punta ai Service corretti)
    @PostMapping("/save/{tipo}")
    public String salvaModello(@PathVariable String tipo, @RequestParam Map<String, String> params, HttpSession session) {
        Utente admin = gestoreSessione.getUtenteCorrente(session);
        if (admin == null || !"ADMIN".equals(admin.getRuolo())) return "redirect:/login";

        try {
            switch (tipo) {
                case "utenti":
                    Utente u = new Utente();
                    u.setNome(params.get("nome"));
                    u.setCognome(params.get("cognome"));
                    u.setEmail(params.get("email"));
                    u.setUsername(params.get("username"));
                    u.setPassword(params.get("password"));
                    u.setRuolo(params.get("ruolo"));
                    utenteService.addUtente(u);
                    break;
                case "voli":
                    Volo v = new Volo();
                    v.setCodiceVolo(params.get("codiceVolo"));
                    v.setIdCittaPartenza(Long.parseLong(params.get("idCittaPartenza")));
                    v.setIdCittaArrivo(Long.parseLong(params.get("idCittaArrivo")));
                    v.setOrarioPartenza(LocalDateTime.parse(params.get("orarioPartenza")));
                    v.setOrarioArrivo(LocalDateTime.parse(params.get("orarioArrivo")));
                    v.setPrezzoBase(BigDecimal.valueOf(Double.parseDouble(params.get("prezzoBase"))));
                    v.setIdAereo(Long.parseLong(params.get("idAereo")));
                    v.setStato(Volo.StatoVolo.valueOf("SCHEDULED"));
                    voloService.createVolo(v);
                    break;
                case "aerei":
                    Aereo a = new Aereo();
                    a.setProduttore(params.get("produttore"));
                    a.setModello(params.get("modello"));
                    a.setCapacitaEconomy(Integer.parseInt(params.get("capacitaEconomy")));
                    a.setCapacitaBusiness(Integer.parseInt(params.get("capacitaBusiness")));
                    aereoService.addAereo(a);
                    break;
                case "citta":
                    Citta c = new Citta();
                    c.setNome(params.get("nome"));
                    c.setNazione(params.get("nazione"));
                    c.setCodiceIata(params.get("codiceIata"));
                    cittaService.addCitta(c);
                    break;
                case "equipaggio":
                    equipaggioService.assegnaMembro(Long.parseLong(params.get("idVolo")), Long.parseLong(params.get("idUtente")));
                    break;
            }
        } catch (Exception e) {
            return "redirect:/admin?error=true&msg=" + e.getMessage();
        }
        return "redirect:/admin?success=true";
    }
}