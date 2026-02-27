package com.skywings.controller;

import com.skywings.model.Citta;
import com.skywings.model.Volo;
import com.skywings.service.CittaService;
import com.skywings.service.VoloService; // Import aggiunto
import com.skywings.util.GestoreSessione;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final GestoreSessione gestoreSessione;
    private final VoloService voloService;
    private final CittaService cittaService;

    public HomeController(GestoreSessione gestoreSessione, VoloService voloService, CittaService cittaService) {
        this.gestoreSessione = gestoreSessione;
        this.voloService = voloService;
        this.cittaService = cittaService;
    }

    @GetMapping("/")
    public String index(Model model) {
        // Recupera la lista dei voli dal DB
        List<Volo> voli = voloService.getAllVoli();

        // Crea la mappa ID -> NOME (Opzione 2)
        // Trasforma la lista delle città in una mappa per un lookup veloce nell'HTML
        Map<Long, String> nomiCitta = cittaService.getAllCitta().stream()
                .collect(Collectors.toMap(Citta::getId, Citta::getNome));

        model.addAttribute("voli", voli);
        model.addAttribute("nomiCitta", nomiCitta);
        return "index";
    }

    @GetMapping("/flights/search-results")
    public String searchFlights(@RequestParam String origin,
                                @RequestParam String destination,
                                Model model) {
        // LOGICA PROBABILISTICA: Qui salvi la ricerca dell'utente
        // origin e destination sono STRINGHE (es. "Berlino"), così catturi la domanda
        //analyticsService.salvaRicerca(origin, destination);

        //List<Volo> risultati = voloService.cercaPerCitta(origin, destination);
        //model.addAttribute("voli", risultati);
        return "fragments/search-results :: results";
    }
}