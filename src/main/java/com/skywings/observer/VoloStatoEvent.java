package com.skywings.observer;

import com.skywings.model.Volo;

public class VoloStatoEvent {
    private final Long voloId;
    private final String codiceVolo;
    private final Volo.StatoVolo nuovoStato; // Cambiato in Enum

    public VoloStatoEvent(Long voloId, String codiceVolo, Volo.StatoVolo nuovoStato) {
        this.voloId = voloId;
        this.codiceVolo = codiceVolo;
        this.nuovoStato = nuovoStato;
    }

    public Long getVoloId() { return voloId; }
    public String getCodiceVolo() { return codiceVolo; }
    public Volo.StatoVolo getNuovoStato() { return nuovoStato; }
}