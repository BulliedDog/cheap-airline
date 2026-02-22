package com.skywings.repository.interfaces;

import com.skywings.model.Utente;
import java.util.Optional;

public interface UtenteDAO {
    Utente save(Utente utente);
    Optional<Utente> trovaPerUsername(String username);
}