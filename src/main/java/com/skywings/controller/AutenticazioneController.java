package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.service.AutenticazioneService;
import com.skywings.util.GestoreSessione;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AutenticazioneController {

    private final AutenticazioneService authService;
    private final GestoreSessione gestoreSessione;

    public AutenticazioneController(AutenticazioneService authService, GestoreSessione gestoreSessione) {
        this.authService = authService;
        this.gestoreSessione = gestoreSessione;
    }

    // Pagina di login/registrazione
    @GetMapping("/login")
    public String paginaLogin() {
        return "login";
    }

    // Ricezione dei dati in POST dalla pagina di login
    @PostMapping("/login")
    public String postLogin(@RequestParam String username,
                              @RequestParam String password,
                              HttpSession session,
                              Model model) {

        Utente utente = authService.login(username, password);

        if (utente != null) {
            gestoreSessione.salvaUtente(session, utente);
            return "index";
        } else {
            model.addAttribute("errore", "Credenziali non valide!");
            return "login";
        }
    }

    @PostMapping("/register")
    public String postRegister(@RequestParam String username,
                               @RequestParam String email,
                               @RequestParam String password,
                               @RequestParam String nome,
                               @RequestParam String cognome,
                               HttpSession session,
                               Model model) {

        // 1. Creiamo l'oggetto Utente con i dati del form
        Utente nuovoUtente = new Utente();
        nuovoUtente.setUsername(username);
        nuovoUtente.setEmail(email);
        nuovoUtente.setPassword(password); // Se hai un encoder, usalo qui!
        nuovoUtente.setNome(nome);
        nuovoUtente.setCognome(cognome);
        nuovoUtente.setRuolo("UTENTE");

        try {
            // 2. Salviamo l'utente nel database tramite il service
            // Assumo che il tuo authService abbia un metodo per registrare
            Utente utenteSalvato = authService.registra(nuovoUtente);

            if (utenteSalvato != null) {
                // 3. Login Automatico: salviamo l'utente appena creato nella sessione
                gestoreSessione.salvaUtente(session, utenteSalvato);

                // Reindirizziamo alla home (o index)
                return "redirect:/";
            } else {
                model.addAttribute("errore", "Errore durante la registrazione. Riprova.");
                return "login";
            }
        } catch (Exception e) {
            // Gestione errore (es: username o email già esistenti)
            model.addAttribute("errore", "Username o Email già utilizzati!");
            return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        gestoreSessione.cancellaSessione(session);
        return "redirect:/login";
    }
}