package com.skywings.service;

import com.skywings.model.Volo;
import com.skywings.repository.interfaces.VoloDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class VoloService {

    @Autowired
    private VoloDAO voloDAO;

    public List<Volo> getAllVoli() {
        return voloDAO.findAll();
    }

    public Volo getVoloById(Long id) {
        return voloDAO.findById(id).orElse(null);
    }

    public List<Volo> getVoliFiltered(Long originId, Long destId, LocalDate date) {
        return voloDAO.findVoliFiltered(originId, destId, date);
    }

    public void createVolo(Volo volo) {
        voloDAO.save(volo);
    }

    public void updateVolo(Volo volo) {
        voloDAO.update(volo);
    }

    public void deleteVolo(Long id) {
        voloDAO.deleteById(id);
    }
}