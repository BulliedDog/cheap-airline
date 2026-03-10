package com.skywings.controller;

import com.skywings.model.Utente;
import com.skywings.service.PrenotazioneService;
import com.skywings.util.GestoreSessione;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import jakarta.servlet.http.HttpSession;

@Controller
public class PrenotazioneController {

    private final PrenotazioneService prenotazioneService;
    private final GestoreSessione gestoreSessione;

    public PrenotazioneController(PrenotazioneService prenotazioneService,
                                  GestoreSessione gestoreSessione) {
        this.prenotazioneService = prenotazioneService;
        this.gestoreSessione = gestoreSessione;
    }

    @PostMapping("/prenotazioni/book")
    public String bookFlight(
            @RequestParam Long voloId,
            @RequestParam String nomePasseggero,
            @RequestParam String cognomePasseggero,
            @RequestParam String classe,
            @RequestParam String posto,
            @RequestParam String numeroDocumento,
            HttpSession session,
            RedirectAttributes redirectAttributes) {

        // Controllo di sicurezza: recuperiamo l'utente dalla sessione
        Utente utente = gestoreSessione.getUtenteCorrente(session);

        if (utente == null) {
            // Se in qualche modo ha aggirato l'HTML, lo blocchiamo e rimandiamo al login
            return "redirect:/login";
        }

        try {
            // Tentiamo di prenotare
            prenotazioneService.creaPrenotazione(utente.getId(), voloId, nomePasseggero, cognomePasseggero, numeroDocumento, classe, posto);
            // Messaggio di successo che verrà letto da HTML
            redirectAttributes.addFlashAttribute("successo", "Booking confirmed! Enjoy your flight.");

        } catch (IllegalStateException e) {
            // Eccezione lanciata se l'aereo è pieno in quella classe
            redirectAttributes.addFlashAttribute("errore", e.getMessage());
        }

        // Rimandiamo l'utente alla stessa pagina del volo per fargli vedere il messaggio
        return "redirect:/flights/detail/" + voloId;
    }
}