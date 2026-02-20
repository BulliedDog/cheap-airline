package com.skywings.model;

public class VoloEquipaggio {
    private Long idVolo;
    private Long idUtente;
    private String noteAssegnazione;

    public VoloEquipaggio() {}

    public VoloEquipaggio(Long idVolo, Long idUtente, String noteAssegnazione) {
        this.idVolo = idVolo;
        this.idUtente = idUtente;
        this.noteAssegnazione = noteAssegnazione;
    }

    public Long getIdVolo() { return idVolo; }
    public void setIdVolo(Long idVolo) { this.idVolo = idVolo; }

    public Long getIdUtente() { return idUtente; }
    public void setIdUtente(Long idUtente) { this.idUtente = idUtente; }

    public String getNoteAssegnazione() { return noteAssegnazione; }
    public void setNoteAssegnazione(String noteAssegnazione) { this.noteAssegnazione = noteAssegnazione; }
}