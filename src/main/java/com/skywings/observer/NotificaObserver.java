package com.skywings.observer;

import com.skywings.model.Prenotazione;
import com.skywings.repository.interfaces.PrenotazioneDAO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.sql.PreparedStatement;
import java.util.ArrayList;
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

    // Usiamo TransactionalEventListener per assicurarci che la notifica
    // parta solo se l'aggiornamento del volo è stato confermato nel DB
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleVoloStatoCambiato(VoloStatoEvent event) {
        List<Prenotazione> prenotazioni = prenotazioneDAO.findByVoloId(event.getVoloId());

        Set<Long> utentiUnici = prenotazioni.stream()
                .map(Prenotazione::getUtenteId)
                .collect(Collectors.toSet());

        if (utentiUnici.isEmpty()) return;

        String statoTesto = event.getNuovoStato().name().replace("_", " ");
        String messaggio = "Il volo " + event.getCodiceVolo() + " è ora in stato: " + statoTesto;

        List<Long> listaUtenti = new ArrayList<>(utentiUnici);

        String sql = "INSERT INTO notifiche (utente_id, messaggio) VALUES (?, ?)";

        // Eseguiamo un batch update per ottimizzare le prestazioni
        jdbcTemplate.batchUpdate(sql, listaUtenti, 50, (PreparedStatement ps, Long utenteId) -> {
            ps.setLong(1, utenteId);
            ps.setString(2, messaggio);
        });
    }
}