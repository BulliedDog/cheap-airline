package com.skywings.controller;

import com.skywings.model.*;
import com.skywings.service.*;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired private AereoService aereoService;
    @Autowired private CittaService cittaService;
    @Autowired private VoloService voloService;
    @Autowired private UtenteService utenteService;
    @Autowired private VoloEquipaggioService voloEquipaggioService;
    @Autowired private PrenotazioneService prenotazioneService;

    // --- MAPPE FUNZIONALI (Addio Switch!) ---
    private Map<String, Supplier<List<?>>> listaProviders;
    private Map<String, Supplier<Object>> nuovoProviders;
    private Map<String, Function<Long, Object>> editProviders;
    private Map<String, Consumer<Long>> deleteProviders;
    private Map<String, String> titoli;

    @PostConstruct
    public void init() {
        // Mappa per ottenere le liste (Read)
        listaProviders = Map.of(
                "aerei", () -> aereoService.getAllAerei(),
                "citta", () -> cittaService.getAllCitta(),
                "voli", () -> voloService.getAllVoli(),
                "utenti", () -> utenteService.getAllUtenti(),
                "equipaggi", () -> voloEquipaggioService.getAllEquipaggio(),
                "prenotazioni", () -> prenotazioneService.getAllPrenotazioni()
        );

        // Mappa per istanziare oggetti vuoti (New)
        nuovoProviders = Map.of(
                "aerei", Aereo::new,
                "citta", Citta::new,
                "voli", Volo::new,
                "utenti", Utente::new,
                "equipaggi", VoloEquipaggio::new,
                "prenotazioni", Prenotazione::new
        );

        // Mappa per trovare l'oggetto dal DB (Edit)
        editProviders = Map.of(
                "aerei", id -> aereoService.getAereoById(id),
                "citta", id -> cittaService.getCittaById(id),
                "voli", id -> voloService.getVoloById(id),
                "utenti", id -> utenteService.getUtenteById(id),
                "prenotazioni", id -> prenotazioneService.getPrenotazioneById(id)
        );

        // Mappa per le eliminazioni (Delete)
        deleteProviders = Map.of(
                "aerei", id -> aereoService.deleteAereo(id),
                "citta", id -> cittaService.deleteCitta(id),
                "voli", id -> voloService.deleteVolo(id),
                "utenti", id -> utenteService.deleteUtente(id),
                "prenotazioni", id -> prenotazioneService.deletePrenotazioneById(id)
        );

        // Titoli delle tabelle
        titoli = Map.of(
                "aerei", "Gestione Flotta Aerei", "citta", "Gestione Città",
                "voli", "Gestione Voli", "utenti", "Gestione Utenti",
                "equipaggi", "Gestione Equipaggi", "prenotazioni", "Gestione Prenotazioni"
        );
    }

    private void caricaConteggi(Model model) {
        model.addAttribute("countAerei", aereoService.getAllAerei().size());
        model.addAttribute("countCitta", cittaService.getAllCitta().size());
        model.addAttribute("countVoli", voloService.getAllVoli().size());
        model.addAttribute("countUtenti", utenteService.getAllUtenti().size());
        model.addAttribute("countEquipaggi", voloEquipaggioService.getAllEquipaggio().size());
        model.addAttribute("countPrenotazioni", prenotazioneService.getAllPrenotazioni().size());
    }

    // --- ROTTE DINAMICHE PULITE ---

    @GetMapping({"", "/", "/dashboard"})
    public String dashboard(Model model) {
        caricaConteggi(model);
        model.addAttribute("titolo", "Dashboard Amministratore");
        return "admin-dashboard";
    }

    @GetMapping("/{modelName}")
    public String lista(@PathVariable String modelName, Model model) {
        String key = modelName.toLowerCase();
        if (!listaProviders.containsKey(key)) return "redirect:/admin/dashboard";

        caricaConteggi(model);
        model.addAttribute("modelName", key);
        model.addAttribute("items", listaProviders.get(key).get()); // Esegue il metodo salvato nella mappa!
        model.addAttribute("titoloTabella", titoli.get(key));

        return "admin-dashboard";
    }

    @GetMapping("/{modelName}/new")
    public String nuovo(@PathVariable String modelName, Model model) {
        String key = modelName.toLowerCase();
        if (!nuovoProviders.containsKey(key)) return "redirect:/admin/dashboard";

        model.addAttribute("modelName", key);
        model.addAttribute("azione", "Nuovo " + key);
        model.addAttribute("entity", nuovoProviders.get(key).get());

        return "universal-form";
    }

    @GetMapping("/{modelName}/edit/{id}")
    public String modifica(@PathVariable String modelName, @PathVariable Long id, Model model) {
        String key = modelName.toLowerCase();
        if (!editProviders.containsKey(key)) return "redirect:/admin/dashboard";

        model.addAttribute("modelName", key);
        model.addAttribute("azione", "Modifica " + key);
        model.addAttribute("entity", editProviders.get(key).apply(id));

        return "universal-form";
    }

    @GetMapping("/{modelName}/delete/{id}")
    public String elimina(@PathVariable String modelName, @PathVariable Long id) {
        String key = modelName.toLowerCase();
        if (deleteProviders.containsKey(key)) {
            deleteProviders.get(key).accept(id);
        }
        return "redirect:/admin/" + key;
    }

    // --- GESTIONE ECCEZIONE CHIAVE COMPOSTA (EQUIPAGGI) ---

    // ELIMINA EQUIPAGGIO (Chiave composta: voloId + utenteId)
    @GetMapping("/equipaggi/delete/{voloId}/{utenteId}")
    public String eliminaEquipaggio(@PathVariable Long voloId, @PathVariable Long utenteId) {
        // Ora possiamo passare entrambi i parametri al metodo corretto!
        voloEquipaggioService.rimuoviMembro(voloId, utenteId);
        return "redirect:/admin/equipaggi";
    }

    // MODIFICA EQUIPAGGIO (Se hai bisogno di modificare l'assegnazione)
    @GetMapping("/equipaggi/edit/{voloId}/{utenteId}")
    public String modificaEquipaggio(@PathVariable Long voloId, @PathVariable Long utenteId, Model model) {
        model.addAttribute("modelName", "equipaggi");
        model.addAttribute("azione", "Modifica Equipaggio");
        // Assicurati di avere questo metodo nel service:
        model.addAttribute("entity", voloEquipaggioService.getAssegnazioneByIds(voloId, utenteId));
        return "universal-form";
    }

    // --- SALVATAGGIO ---
    // Rimangono separati per una ragione architetturale fondamentale: sfruttare il Data Binding di Spring (@ModelAttribute).
    // Questo evita di dover parsare manualmente stringhe in Date o BigDecimal come facevi in passato con Map<String, String>[cite: 8].

    @PostMapping("/aerei/save") public String saveAereo(@ModelAttribute Aereo aereo) { aereoService.addAereo(aereo); return "redirect:/admin/aerei"; }
    @PostMapping("/citta/save") public String saveCitta(@ModelAttribute Citta citta) { cittaService.addCitta(citta); return "redirect:/admin/citta"; }
    @PostMapping("/voli/save") public String saveVolo(@ModelAttribute Volo volo) { voloService.createVolo(volo); return "redirect:/admin/voli"; }
    @PostMapping("/utenti/save") public String saveUtente(@ModelAttribute Utente utente) { utenteService.addUtente(utente); return "redirect:/admin/utenti"; }
    @PostMapping("/equipaggi/save") public String saveEquipaggio(@ModelAttribute VoloEquipaggio eq) { voloEquipaggioService.addMembro(eq); return "redirect:/admin/equipaggi"; }
    @PostMapping("/prenotazioni/save") public String savePrenotazione(@ModelAttribute Prenotazione pre) { prenotazioneService.addPrenotazione(pre); return "redirect:/admin/prenotazioni"; }
}