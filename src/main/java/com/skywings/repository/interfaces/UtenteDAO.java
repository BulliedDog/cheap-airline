package com.skywings.repository.interfaces;

import com.skywings.model.Utente;
import java.util.List;
import java.util.Optional;

public interface UtenteDAO {
    List<Utente> findAll();
    Optional<Utente> findById(Long id);
    Optional<Utente> findByUsername(String username);
    Optional<Utente> findByEmail(String email);
    Utente save(Utente utente);
    void update(Utente utente);
    void deleteById(Long id);
}