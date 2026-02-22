package com.skywings.service;

import com.skywings.model.Citta;
import com.skywings.repository.interfaces.CittaDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CittaService {

    @Autowired
    private CittaDAO cittaDAO;

    public List<Citta> getAllCitta() {
        return cittaDAO.findAll();
    }

    public Citta getCittaById(Long id) {
        return cittaDAO.findById(id)
                .orElseThrow(() -> new RuntimeException("Città non trovata con ID: " + id));
    }

    public void createCitta(Citta citta) {
        cittaDAO.save(citta);
    }

    public void updateCitta(Citta citta) {
        cittaDAO.update(citta);
    }

    public void deleteCitta(Long id) {
        cittaDAO.deleteById(id);
    }
}