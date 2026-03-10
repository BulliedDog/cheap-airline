package com.skywings.mapper;

import com.skywings.dto.PrenotazioneDTO;
import com.skywings.model.Prenotazione;
import org.springframework.stereotype.Component;

@Component
public class PrenotazioneMapper {

    public PrenotazioneDTO toDto(Prenotazione entity) {
        if (entity == null) return null;
        PrenotazioneDTO dto = new PrenotazioneDTO();
        dto.setId(entity.getId());
        dto.setVoloId(entity.getVoloId());
        dto.setUtenteId(entity.getUtenteId());
        dto.setNomePasseggero(entity.getNomePasseggero());
        dto.setCognomePasseggero(entity.getCognomePasseggero());
        dto.setNumeroDocumento(entity.getNumeroDocumento());
        dto.setDataPrenotazione(entity.getDataPrenotazione());
        dto.setPrezzoAcquistato(entity.getPrezzoAcquistato());
        dto.setPosto(entity.getPosto());
        dto.setClasse(entity.getClasse());
        return dto;
    }

    public Prenotazione toEntity(PrenotazioneDTO dto) {
        if (dto == null) return null;
        Prenotazione entity = new Prenotazione();
        entity.setId(dto.getId());
        entity.setVoloId(dto.getVoloId());
        entity.setUtenteId(dto.getUtenteId());
        entity.setNomePasseggero(dto.getNomePasseggero());
        entity.setCognomePasseggero(dto.getCognomePasseggero());
        entity.setNumeroDocumento(dto.getNumeroDocumento());
        entity.setDataPrenotazione(dto.getDataPrenotazione());
        entity.setPrezzoAcquistato(dto.getPrezzoAcquistato());
        entity.setPosto(dto.getPosto());
        entity.setClasse(dto.getClasse());
        return entity;
    }
}