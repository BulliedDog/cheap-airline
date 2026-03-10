package com.skywings.controller;

import com.skywings.dto.VoloDTO;
import com.skywings.model.Volo;
import com.skywings.service.AereoService;
import com.skywings.service.CittaService;
import com.skywings.service.VoloService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Controller
public class VoloController {

    private final VoloService voloService;
    private final CittaService cittaService;
    private final AereoService aereoService;

    public VoloController(VoloService voloService,
                          CittaService cittaService,
                          AereoService aereoService){
        this.voloService = voloService;
        this.cittaService = cittaService;
        this.aereoService = aereoService;
    }

    @PostMapping("/flights/search")
    public String searchFlights(
            @RequestParam(required = false) Long originId,
            @RequestParam(required = false) Long destinationId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate departureDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
            Model model) {

        List<VoloDTO> filteredFlights;

        // Se l'utente non ha inserito nessun parametro, mostriamo tutti i voli
        if (originId == null && destinationId == null && departureDate == null) {
            filteredFlights = voloService.getAllVoliConPrezzo();
        }
        // Altrimenti, filtriamo in base a ciò che è stato passato (anche solo la destinazione)
        else {
            filteredFlights = voloService.getVoliFilteredConPrezzo(originId, destinationId, departureDate);
        }

        model.addAttribute("voli", filteredFlights);
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());
        model.addAttribute("cittaCasuali", cittaService.getCittaConVoliProgrammatiLimit4());
        model.addAttribute("aereiCasuali", aereoService.getAereiCasualiLimit2());

        // Cerchiamo i voli di ritorno solo se ci sono originId, destinationId e la returnDate
        if (returnDate != null && originId != null && destinationId != null) {
            List<VoloDTO> returnFlights = voloService.getVoliFilteredConPrezzo(destinationId, originId, returnDate);
            model.addAttribute("voliRitorno", returnFlights);
        }

        return "index";
    }

    @GetMapping("/flights/detail/{id}")
    public String flightDetail(@PathVariable Long id, Model model) {
        VoloDTO volo = voloService.getVoloByIdConPrezzo(id);
        if (volo == null) {
            return "redirect:/";
        }

        model.addAttribute("volo", volo);
        model.addAttribute("nomiCitta", cittaService.getMappaNomiCitta());

        return "flight-detail";
    }
}