package com.skywings.repository;

import com.skywings.model.Utente;
import java.util.Optional;

public interface UtenteDAO {
    Utente save(Utente utente);
    Optional<Utente> trovaPerUsername(String username);
}