package com.skywings.repository.interfaces;

import com.skywings.model.Prenotazione;
import java.util.List;

public interface PrenotazioneDAO {
    void save(Prenotazione prenotazione);
    List<Prenotazione> findByUtenteId(Long utenteId);
    List<Prenotazione> findByVoloId(Long voloId);
    List<Prenotazione> findAll();
    Prenotazione findById(Long id);

    void deleteById(Long id);
}