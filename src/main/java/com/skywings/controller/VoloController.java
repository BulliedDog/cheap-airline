package com.skywings.controller;

import com.skywings.model.Volo;
import com.skywings.service.CittaService;
import com.skywings.service.VoloService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class VoloController {

    private VoloService voloService;
    private CittaService cittaService;

    public VoloController(VoloService voloService, CittaService cittaService){
        this.voloService = voloService;
        this.cittaService = cittaService;
    }

    @GetMapping("/flights/search")
    public String searchFlights(
            @RequestParam Long originId,
            @RequestParam Long destinationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
            Model model) {

        List<Volo> filteredFlights = voloService.getVoliFiltered(originId, destinationId, departureDate);

        model.addAttribute("voli", filteredFlights);
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());

        // Se l'utente ha inserito una data di ritorno, cerchiamo i voli inversi
        if (returnDate != null) {
            List<Volo> returnFlights = voloService.getVoliFiltered(destinationId, originId, returnDate);
            model.addAttribute("voliRitorno", returnFlights);
        }

        return "index"; // Torna alla tua index con la tabella aggiornata
    }

    @GetMapping("/flights/detail/{id}")
    public String flightDetail(@PathVariable Long id, Model model) {
        Volo volo = voloService.getVoloById(id);
        if (volo == null) {
            return "redirect:/";
        }

        model.addAttribute("volo", volo);
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());

        return "flight-detail";
    }
}
