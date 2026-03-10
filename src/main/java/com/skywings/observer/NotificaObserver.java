package com.skywings.observer;

import com.skywings.model.Prenotazione;
import com.skywings.observer.VoloStatoEvent;
import com.skywings.repository.interfaces.PrenotazioneDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class NotificaObserver {

    private final PrenotazioneDAO prenotazioneDAO;
    private final JdbcTemplate jdbcTemplate;

    public NotificaObserver(PrenotazioneDAO prenotazioneDAO, JdbcTemplate jdbcTemplate) {
        this.prenotazioneDAO = prenotazioneDAO;
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener
    public void handleVoloStatoCambiato(VoloStatoEvent event) {
        List<Prenotazione> prenotazioni = prenotazioneDAO.findByVoloId(event.getVoloId());

        Set<Long> utentiUnici = prenotazioni.stream()
                .map(Prenotazione::getUtenteId)
                .collect(Collectors.toSet());

        String sql = "INSERT INTO notifiche (utente_id, messaggio) VALUES (?, ?)";

        // Convertiamo l'enum in testo leggibile
        String statoTesto = event.getNuovoStato().name().replace("_", " ");
        String messaggio = "Il volo " + event.getCodiceVolo() + " è ora in stato: " + statoTesto;

        for (Long utenteId : utentiUnici) {
            jdbcTemplate.update(sql, utenteId, messaggio);
        }
    }
}