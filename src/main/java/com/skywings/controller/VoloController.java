package com.skywings.controller;

import com.skywings.model.Volo;
import com.skywings.service.CittaService;
import com.skywings.service.VoloService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class VoloController {

    private VoloService voloService;
    private CittaService cittaService;

    public VoloController(VoloService voloService, CittaService cittaService){
        this.voloService = voloService;
        this.cittaService = cittaService;
    }

    @GetMapping("/flights/detail/{id}")
    public String showFlightDetail(@PathVariable("id") Long id, Model model) {
        // 1. Recupera il volo specifico tramite ID
        Volo volo = voloService.getVoloById(id);

        if (volo == null) {
            return "redirect:/?error=flight_not_found";
        }

        // 2. Recupera la mappa dei nomi delle città (come abbiamo fatto per la home)
        //Map<Long, String> nomiCitta = cittaService.getMappaNomiCitta();

        // 3. (Opzionale) Potresti voler recuperare anche i dettagli dell'aereo
        // Aereo aereo = aereoService.getAereoById(volo.getIdAereo());
        // model.addAttribute("aereo", aereo);

        model.addAttribute("volo", volo);
        //model.addAttribute("nomiCitta", nomiCitta);

        return "flight-detail";
    }
}
